package com.ttn.reap.controller;

import com.ttn.reap.co.RecognizeCO;
import com.ttn.reap.dto.BadgeTransactionDto;
import com.ttn.reap.dto.UserDto;
import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
import com.ttn.reap.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Value("${spring.mail.username}")
    String fromMail;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;
    @Autowired
    private DateService dateService;


    @GetMapping("/")
    String main(HttpSession session, Model model) throws ParseException {
        return sessionCheck(session, model);
    }

    @GetMapping("signup")
    ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("register")
    String submit(Model model, @ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        User user1 = userService.findUserByEmail(user.getEmail());
        if (user1 != null) {
            model.addAttribute("err", "Email already registered");
            return "signup";
        } else {
            String fileName = fileStorageService.storeFile(file);
            user.setFileName("/upload/" + fileName);
            userService.save(user);
            return "login";
        }
    }

    @GetMapping("register")
    String checkRegister() {
        return "redirect:/";
    }

    @GetMapping("login")
    ModelAndView login(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (session.getAttribute("userId") == null) {
            modelAndView.setViewName("login");
            modelAndView.addObject("user", new User());
        } else {
            modelAndView.setViewName("redirect:/user");
        }
        return modelAndView;
    }

    @PostMapping("user")
    String user(@ModelAttribute("user") User user, Model model, HttpSession session) throws ParseException {
        User checkuser = userService.checkemailandpassword(user.getEmail(), user.getPassword());
        if (checkuser == null) {
            model.addAttribute("valid", "Enter valid username and password!!!");
            return "login";
        } else if (checkuser.isActive() == false) {
            model.addAttribute("valid", "Account Inactive, Contact Admin.....!!!");
            return "login";
        } else {
            String startDate = "1900-01-01";
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            session.setAttribute("userId", checkuser.getId());
            session.setAttribute("startDate", startDate);
            String end = format.format(new Date());
            session.setAttribute("endDate", end);
            return dashboardData(checkuser, model, format.parse(startDate), new Date());
        }
    }

    private String dashboardData(User checkuser, Model model, Date start, Date end) {
        BadgeBalance badge = badgeBalanceService.getBadgeById(checkuser.getId());
        long gold = badgeTransactionService.countByReceiverAndBadge(checkuser, Badge.GOLD);
        long silver = badgeTransactionService.countByReceiverAndBadge(checkuser, Badge.SILVER);
        long bronze = badgeTransactionService.countByReceiverAndBadge(checkuser, Badge.BRONZE);
        List<BadgeTransaction> badgeTransactionList = badgeTransactionService.findAllByDateBetweenOrderByDateDesc(start, end);
        List<BadgeTransactionDto> badgeTransactionDtos = badgeTransactionService.findMaxBadgeCount().subList(0, 3);
        List<User> userList = badgeTransactionDtos.stream().map(BadgeTransactionDto::getReceiver).collect(Collectors.toList());
        int i = 0;
        List<Long> goldList = new ArrayList<>();
        List<Long> silverList = new ArrayList<>();
        List<Long> bronzeList = new ArrayList<>();
        for (i = 0; i < 3; i++) {
            long golditem = badgeTransactionService.countByReceiverAndBadge(userList.get(i), Badge.GOLD);
            long silveritem = badgeTransactionService.countByReceiverAndBadge(userList.get(i), Badge.SILVER);
            long bronzeitem = badgeTransactionService.countByReceiverAndBadge(userList.get(i), Badge.BRONZE);
            goldList.add(golditem);
            silverList.add(silveritem);
            bronzeList.add(bronzeitem);
        }
        boolean role = checkuser.isAdmin();
        model.addAttribute("badgetransactionlist", badgeTransactionList);
        model.addAttribute("user", checkuser);
        model.addAttribute("badge", badge);
        model.addAttribute("role", role);
        model.addAttribute("gold", gold);
        model.addAttribute("silver", silver);
        model.addAttribute("bronze", bronze);
        model.addAttribute("recognizeco", new RecognizeCO());
        model.addAttribute("newer", badgeTransactionDtos);
        model.addAttribute("goldlist", goldList);
        model.addAttribute("silverlist", silverList);
        model.addAttribute("bronzelist", bronzeList);
        return "dashboard";
    }

    @GetMapping("user")
    String user(HttpSession session, Model model) throws ParseException {
        return sessionCheck(session, model);
    }

    String sessionCheck(HttpSession session, Model model) throws ParseException {
        if (session.getAttribute("userId") != null) {
            User user = userService.findUserId((long) session.getAttribute("userId"));
            String start = (String) session.getAttribute("startDate");
            String end = (String) session.getAttribute("endDate");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return dashboardData(user, model, format.parse(start), dateService.solveDate(end));
        } else {
            return "redirect:/login";
        }
    }

    private boolean getRoleofUser(User checkuser) {
        return checkuser.getRole().equals(Role.USER);
    }

    @GetMapping("logout")
    private String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }

    @GetMapping("forgotPassword")
    public String forgotPassword(Model model) {
        return "forgotPassword";
    }

    @PostMapping("forgotSubmit")
    public String forgotSubmit(@RequestParam String email, HttpServletRequest request, Model model) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email id not registered!!!");
            return "forgotPassword";
        } else {
            userService.saveToken(user);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromMail);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Forgot Password Link");
            mailMessage.setText("To reset your password, click the link below: \n" + appUrl + ":8080/reset?token=" + user.getToken());
            emailService.sendEmail(mailMessage);
            model.addAttribute("user",new User());
            return "redirect:/login";
        }
    }

    
    @GetMapping("/reset")
    public String resetPassByToken(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("user", new User());
        return "resetPassword";
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestParam Map<String, String> requestParamas, Model model) {
        User user = userService.findUserByToken(requestParamas.get("token"));
        user.setPassword(requestParamas.get("password"));
        user.setToken(null);
        userService.save(user);
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("getUserListActive")
    @ResponseBody
    public List<UserDto> getUserListActive(@RequestParam String term, @RequestParam String user_id) {
        return userService.simulateSearchResult(term, Long.parseLong(user_id));
    }

    @PostMapping("recognize")
    public String recognizeNewer(@ModelAttribute RecognizeCO recognizeCO) {
        int s = recognizeCO.getReceiver_email().indexOf("(");
        int e = recognizeCO.getReceiver_email().indexOf(")");
        recognizeCO.setReceiver_email(recognizeCO.getReceiver_email().substring(s + 1, e));
        User sender = userService.findUserByEmail(recognizeCO.getSender_email());
        User receiver = userService.findUserByEmail(recognizeCO.getReceiver_email());
        Badge badge;
        if (recognizeCO.getBadge_val().equalsIgnoreCase("gold")) {
            badge = Badge.GOLD;
        } else if (recognizeCO.getBadge_val().equalsIgnoreCase("silver")) {
            badge = Badge.SILVER;
        } else {
            badge = Badge.BRONZE;
        }
        badgeTransactionService.saveNewTranscation(sender, receiver, new Date(), recognizeCO.getMessage_val(), badge);
        return "redirect:/user";
    }
}
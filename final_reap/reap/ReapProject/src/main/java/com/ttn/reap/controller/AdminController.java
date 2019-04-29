package com.ttn.reap.controller;

import com.ttn.reap.co.RecognizeCO;
import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.EmailService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;
    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    String fromMail;


    @GetMapping("/manage")
    public ModelAndView manageUsers(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("manageUser");
            long id = (Long) session.getAttribute("userId");
            User user = userService.findUserId(id);
            if(user.isAdmin()!=true){
                ModelAndView modelAndView1 = new ModelAndView("user");
                return modelAndView1;
            }else{
                modelAndView.addObject("user", user);
                BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
                boolean role = user.isAdmin();
                List<User> users = userService.findAllByIdIsNot(id);
                System.out.println(users);
                modelAndView.addObject("users", users);
                List<BadgeBalance> badgeBalances = new ArrayList<>();
                for (User u : users) {
                    badgeBalances.add(badgeBalanceService.getBadgeByUserId(u));
                }
                long gold = badgeTransactionService.countByReceiverAndBadge(user, Badge.GOLD);
                long silver = badgeTransactionService.countByReceiverAndBadge(user, Badge.SILVER);
                long bronze = badgeTransactionService.countByReceiverAndBadge(user, Badge.BRONZE);
                BadgeBalance badge = badgeBalanceService.getBadgeById(user.getId());
                modelAndView.addObject("badge", badge);
                modelAndView.addObject("gold", gold);
                modelAndView.addObject("silver", silver);
                modelAndView.addObject("bronze", bronze);
                modelAndView.addObject("badgeBalance", badgeBalance);
                modelAndView.addObject("badgeBalances", badgeBalances);
                modelAndView.addObject("recognizeco", new RecognizeCO());
//            modelAndView.addObject("role", role);
                return modelAndView;
            }
            }
    }

    @PostMapping("/updateUserRole")
    @ResponseBody
    public void updateUserRole(@RequestParam("role") String role, @RequestParam("userId") String userId) {
        userService.updateUserRole(role, userId);
    }

    @PostMapping("/updateAdminRole")
    @ResponseBody
    public void updateAdminRole(@RequestParam("isAdmin") String isAdmin, @RequestParam("userId") String userId) {
        userService.updateAdminRole(isAdmin,userId);
    }

    @PostMapping("/updateUserActive")
    @ResponseBody
    public void updateUserActive(@RequestParam("isActive") String isActive, @RequestParam("userId") String userId) {
        userService.updateUserActive(isActive, userId);
    }

    @PostMapping("updateAvailPoints")
    @ResponseBody
    public void updateAvailPoints(@RequestParam("availPoints") String availPoints, @RequestParam("userId") String userId) {
        userService.updateAvailPoints(availPoints, userId);
    }

    @PostMapping("updateGoldCount")
    @ResponseBody
    public void updateGoldCount(@RequestParam("goldCount") String goldCount, @RequestParam("userId") String userId) {
        userService.updateGoldCount(goldCount, userId);
    }

    @PostMapping("updateSilverCount")
    @ResponseBody
    public void updateSilverCount(@RequestParam("silverCount") String silverCount, @RequestParam("userId") String userId) {
        userService.updateSilverCount(silverCount, userId);
    }

    @PostMapping("updateBronzeCount")
    @ResponseBody
    public void updateBronzeCount(@RequestParam("bronzeCount") String bronzeCount, @RequestParam("userId") String userId) {
        userService.updateBronzeCount(bronzeCount, userId);
    }

    @PostMapping("/revokeTxn")
    public String revokeTransaction(@RequestParam String optradio, @RequestParam String others, @RequestParam String txnId) {
        emailService.revokeMailSend(fromMail, Long.parseLong(txnId), optradio, others);
        badgeTransactionService.revokeNewTranscation(Long.parseLong(txnId), optradio, others, fromMail);
        return "redirect:/user";
    }
}

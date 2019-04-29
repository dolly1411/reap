package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.PurchaseHistory;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.ItemService;
import com.ttn.reap.service.PurchaseHistoryService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class RedeemController {

    @Autowired
    UserService userService;
    @Autowired
    BadgeBalanceService badgeBalanceService;
    @Autowired
    ItemService itemService;
    @Autowired
    PurchaseHistoryService purchaseHistoryService;

    List<Item> bag = new ArrayList<Item>();

    @GetMapping("/redeem")
    public ModelAndView badge(HttpSession session) {

        if (session.getAttribute("userId") == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView;
        } else {
            return redeem(new Item(), session);
        }
    }

    @PostMapping("/redeem")
    public ModelAndView redeem(@ModelAttribute("item") Item item, HttpSession session) {
        List<Item> itemList = itemService.findAll();
        ModelAndView modelAndView = new ModelAndView("redeem");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        boolean role = user.isAdmin();
        modelAndView.addObject("role", role);
        modelAndView.addObject("user", user);
        modelAndView.addObject("badge", badge);
        modelAndView.addObject("redeem", itemList);
        return modelAndView;
    }

    @GetMapping("additems")
    @ResponseBody
    public Item additem(@RequestParam String itemId) {
        Item item = itemService.findById(Long.parseLong(itemId));
        bag.add(item);
        System.out.println("added::" + bag);
        return item;
    }

    @GetMapping("deleteitems")
    @ResponseBody
    public Item deleteitem(@RequestParam String itemId) {
        if(itemId.length()==2){
            itemId = itemId.substring(1);
        }
        Item item = itemService.findById(Long.parseLong(itemId));
        System.out.println("item to be removed" + item);
        System.out.println("bag" + bag);
        for (Iterator<Item> it = bag.iterator(); it.hasNext(); ) {
            Item nextCard = it.next();
            if (nextCard.equals(item)) {
                it.remove();
                System.out.println("removed " + item);
            }
        }
        return item;
    }


    @GetMapping("/redeemPoints")
    public String redeemPoint(HttpSession session) {
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        long totalpoint = 0;
        for (Item item : bag) {
            PurchaseHistory purchaseHistory = new PurchaseHistory(user, item, new Date());
            purchaseHistoryService.save(purchaseHistory);
            totalpoint += item.getItemValue();
        }
        System.out.println("totalpoints::" + totalpoint);
        long deductedpoint = user.getAvailPoints() - totalpoint;
        user.setAvailPoints(deductedpoint);
        long redeemedPoint = user.getRedeemedPoints();
        redeemedPoint += totalpoint;
        user.setRedeemedPoints(redeemedPoint);
        userService.save(user);
        bag.clear();
        return "redirect:/redeem";
    }

}

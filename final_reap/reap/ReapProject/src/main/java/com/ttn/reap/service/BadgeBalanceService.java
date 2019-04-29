package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.BadgeBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BadgeBalanceService {

    @Autowired
    BadgeBalanceRepository badgeBalanceRepository;

    @Autowired
    UserService userService;

    @Transactional(propagation = Propagation.REQUIRED)
    public BadgeBalance setBadgeCount(User user) {
        BadgeBalance badgeBalance = new BadgeBalance(user, 1, 2, 3);
        badgeBalanceRepository.save(badgeBalance);
        return badgeBalance;
    }

    public BadgeBalance getBadgeByUserId(User user) {
        return badgeBalanceRepository.findByUserId(user);
    }
    public BadgeBalance getBadgeById(Long id) {
        return badgeBalanceRepository.getById(id);
    }

    public List<BadgeBalance> getbalancecount() {
        List<BadgeBalance> badgeBalanceList = badgeBalanceRepository.findAllByOrderByGoldCountDescSilverCountDescBronzeCountDesc();
        return badgeBalanceList;
    }

    @Transactional
    public void substractBadgeBalance(User sender, User receiver, Badge badge) {
        BadgeBalance badgeBalance = badgeBalanceRepository.findByUserId(sender);

        if (badge.name().equalsIgnoreCase("GOLD")) {
            badgeBalance.setGoldCount(badgeBalance.getGoldCount() - 1);
        } else if (badge.name().equalsIgnoreCase("SILVER")) {
            badgeBalance.setSilverCount(badgeBalance.getSilverCount() - 1);
        } else {
            badgeBalance.setBronzeCount(badgeBalance.getBronzeCount() - 1);
        }
        userService.updatePointsRecognize(receiver, badge);
    }
    
    @Transactional
    public void addBadgeBalance(User sender,User receiver, Badge badge) {
        BadgeBalance badgeBalance = badgeBalanceRepository.findByUserId(sender);
        if (badge.name().equalsIgnoreCase("GOLD")) {
            badgeBalance.setGoldCount(badgeBalance.getGoldCount() + 1);
        } else if (badge.name().equalsIgnoreCase("SILVER")) {
            badgeBalance.setSilverCount(badgeBalance.getSilverCount() + 1);
        } else {
            badgeBalance.setBronzeCount(badgeBalance.getBronzeCount() + 1);
        }
        userService.updatePointsRevoke(receiver,badge);
    }
}

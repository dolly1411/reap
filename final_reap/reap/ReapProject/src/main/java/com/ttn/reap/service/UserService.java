package com.ttn.reap.service;

import com.ttn.reap.dto.UserDto;
import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeBalanceService badgeBalanceService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        user.setRole(Role.USER);
        userRepository.save(user);
        badgeBalanceService.setBadgeCount(user);
    }

    public User checkemailandpassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public User findUserByToken(String token) {
        return userRepository.findUserByToken(token).orElse(null);
    }

    public User findUserId(long id) {
        return userRepository.findById(id);
    }

    public List<UserDto> findAllByActive(Long id) {
        List<UserDto> userDtos = new ArrayList<>();
        userRepository.findAllByIsActiveTrueAndIdIsNot(id).stream().forEach(e -> userDtos.add(new UserDto(e.getName(), e.getEmail())));
        return userDtos;
    }

    public List<UserDto> simulateSearchResult(String tagName, Long id) {

        List<UserDto> result = new ArrayList<>();

        // iterate a list and filter by tagName
        for (UserDto uto : findAllByActive(id)) {
            if (uto.getName().toLowerCase().contains(tagName.toLowerCase())) {
                result.add(uto);
            }
        }
        return result;
    }

    @Transactional
    public void updatePointsRecognize(User receiver, Badge badge) {
        User user = userRepository.findUserByEmail(receiver.getEmail()).get();
        user.setAvailPoints(user.getAvailPoints() + badge.getValue());
    }

    @Transactional
    public void updatePointsRevoke(User receiver, Badge badge) {
        User user = userRepository.findUserByEmail(receiver.getEmail()).get();
        user.setAvailPoints(user.getAvailPoints() - badge.getValue());
    }

    public List<User> findAllByIdIsNot(Long id) {
        return userRepository.findAllByIdIsNot(id);
    }

    @Transactional
    public void updateUserRole(String role, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
        if (role.equals("SUPERVISOR")) {
            user.setRole(Role.SUPERVISOR);
            badgeBalance.setGoldCount(2);
            badgeBalance.setSilverCount(3);
            badgeBalance.setBronzeCount(6);
        } else if (role.equals("PRACTICE_HEAD")) {
            user.setRole(Role.PRACTICE_HEAD);
            badgeBalance.setGoldCount(3);
            badgeBalance.setSilverCount(6);
            badgeBalance.setBronzeCount(9);
        } else if (role.equals("USER")) {
            user.setRole(Role.USER);
            badgeBalance.setGoldCount(1);
            badgeBalance.setSilverCount(2);
            badgeBalance.setBronzeCount(3);
        }
    }

    @Transactional
    public void updateAdminRole(String isAdmin, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        if (isAdmin.equals("true"))
            user.setAdmin(true);
        else
            user.setAdmin(false);
    }

    @Transactional
    public void updateUserActive(String isActive, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        if (isActive.equals("true"))
            user.setActive(true);
        else
            user.setActive(false);
    }

    @Transactional
    public void updateAvailPoints(String availPoints, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        user.setAvailPoints(Integer.parseInt(availPoints));
    }

    @Transactional
    public void updateGoldCount(String goldCount, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
        badgeBalance.setGoldCount(Integer.parseInt(goldCount));
    }

    @Transactional
    public void updateSilverCount(String silverCount, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
        badgeBalance.setSilverCount(Integer.parseInt(silverCount));
    }

    @Transactional
    public void updateBronzeCount(String bronzeCount, String userId) {
        User user = userRepository.findById(Long.parseLong(userId));
        BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
        badgeBalance.setBronzeCount(Integer.parseInt(bronzeCount));
    }
    
    @Transactional
    public void saveToken(User user){
        user.setToken(UUID.randomUUID().toString());
    }
}
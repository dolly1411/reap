package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    JavaMailSender javaMailSender;
    
    @Autowired
    BadgeTransactionService badgeTransactionService;
    
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }
    
    public void mailSender(String fromMail, User user, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromMail);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        sendEmail(mailMessage);
    }
    
    public void revokeMailSend(String fromMail, long id,String optradio,String others){
        BadgeTransaction badgeTransaction = badgeTransactionService.findBadgeTransactionId(id);
        String message = "Your reap recognition transaction has been revoked by admin. Because of the following reasons: \n";
        String reason;
        int optionValue = Integer.parseInt(optradio);
        if (optionValue == 1)
            reason = "Selected Incorrect Name";
        else if (optionValue == 2)
            reason = "Selected Incorrect Badge";
        else if (optionValue == 3)
            reason = "Inapppropriate Language Used";
        else
            reason = others;
        message = message + reason + "\nTransaction Details: \nSender Name: " + badgeTransaction.getSender().getName() + "\nReceiver Name: " + badgeTransaction.getReceiver().getName() + "\n Badge: " + badgeTransaction.getBadge().toString() + "\nReason: " + badgeTransaction.getReason() +"\nDate: "+badgeTransaction.getDate();
        String subject = "Reap Recognition Transaction Revoked";
        mailSender(fromMail,badgeTransaction.getSender(),subject,message);
        mailSender(fromMail,badgeTransaction.getReceiver(),subject,message);
    }
}



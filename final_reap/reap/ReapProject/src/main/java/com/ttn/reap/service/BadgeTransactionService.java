package com.ttn.reap.service;

import com.ttn.reap.dto.BadgeTransactionDto;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.BadgeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BadgeTransactionService {
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @Autowired
    BadgeBalanceService badgeBalanceService;
    @Autowired
    EmailService emailService;

    public List<BadgeTransaction> findAllByDateBetweenOrderByDateDesc(Date start,Date end) {
        return badgeTransactionRepository.findAllByDateBetweenOrderByDateDesc(start,end);
    }

    public List<BadgeTransaction> findAllByDateBetween(Date start, Date end) {
        return badgeTransactionRepository.findAllByDateBetween(start, end);
    }

    public BadgeTransaction findBadgeTransactionId(long id) {
        return badgeTransactionRepository.findById(id).orElse(null);
    }

    public Long countByReceiverAndBadge(User user, Badge badge) {
        return badgeTransactionRepository.countByReceiverAndBadge(user, badge);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveNewTranscation(User sender, User receiver, Date date, String reason, Badge badge){
        badgeTransactionRepository.save(new BadgeTransaction(sender,receiver,date,reason,badge));
        badgeBalanceService.substractBadgeBalance(sender,receiver,badge);
    }

    public List<BadgeTransaction> findAllByReceiverOrderByDateDesc(User user) {
        return badgeTransactionRepository.findAllByReceiverOrderByDateDesc(user);
    }

    public List<BadgeTransaction> findAllBySenderOrderByDateDesc(User user) {
        return badgeTransactionRepository.findAllBySenderOrderByDateDesc(user);
    }

    public List<BadgeTransaction> findAllBySenderOrReceiverOrderByDateDesc(User receiver, User sender) {
        return badgeTransactionRepository.findAllBySenderOrReceiverOrderByDateDesc(sender, receiver);
    }

    public long countByReceiver(User receiver) {
        return badgeTransactionRepository.countByReceiver(receiver);
    }

    public long countBySender(User sender) {
        return badgeTransactionRepository.countBySender(sender);
    }

    public List<BadgeTransactionDto> findMaxBadgeCount() {
        return badgeTransactionRepository.findMaxBadgeCount();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void revokeNewTranscation(long id,String optradio, String others, String fromMail){
        BadgeTransaction badgeTransaction = badgeTransactionRepository.findById(id).get();
        User sender = badgeTransaction.getSender();
        User receiver = badgeTransaction.getReceiver();
        Badge badge = badgeTransaction.getBadge();
        badgeTransactionRepository.delete(id);
        badgeBalanceService.addBadgeBalance(sender,receiver,badge);
    }
}

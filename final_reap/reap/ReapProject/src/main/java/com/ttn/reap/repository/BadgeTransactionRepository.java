package com.ttn.reap.repository;

import com.ttn.reap.dto.BadgeTransactionDto;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeTransactionRepository extends JpaRepository<BadgeTransaction, Long> {
    Optional<BadgeTransaction> findById(long id);
    
    List<BadgeTransaction> findAll();
    
    List<BadgeTransaction> findAllByDateBetween(Date start, Date end);
    
    List<BadgeTransaction> findAllByDateBetweenOrderByDateDesc(Date start, Date end);
    
    Long countByReceiverAndBadge(@Param("receiver") User receiver, @Param("badge") Badge badge);
    
    List<BadgeTransaction> findAllByReceiverOrderByDateDesc(@Param("receiver") User receiver);
    
    List<BadgeTransaction> findAllBySenderOrderByDateDesc(@Param("sender") User receiver);
    
    List<BadgeTransaction> findAllBySenderOrReceiverOrderByDateDesc(@Param("sender") User sender, @Param("receiver") User receiver);
    
    Long countByReceiver(@Param("receiver") User user);
    
    Long countBySender(@Param("sender") User user);

    @Query("select new com.ttn.reap.dto.BadgeTransactionDto(b.receiver,count(b.badge)) from BadgeTransaction b group by b.receiver order by count(b.badge) desc")
    List<BadgeTransactionDto> findMaxBadgeCount();
}

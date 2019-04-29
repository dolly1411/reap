package com.ttn.reap.repository;

import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.PurchaseHistory;
import com.ttn.reap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {
        PurchaseHistory save(PurchaseHistory purchaseHistory);
        List<PurchaseHistory> findByUserIdOrderByPurchaseTimestampDesc(User user);
}

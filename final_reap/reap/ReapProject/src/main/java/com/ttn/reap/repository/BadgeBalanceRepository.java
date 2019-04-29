package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeBalanceRepository extends JpaRepository<BadgeBalance, Long> {
    BadgeBalance getById(long id);

    List<BadgeBalance> findAllByOrderByGoldCountDescSilverCountDescBronzeCountDesc();

   /* @Query("select badge.bronzeCount,badge.goldCount,badge.silverCount,badge.userId from BadgeBalance badge order by gold_count desc ,silver_count desc,bronze_count desc")
    List<Object[]> getallbycount();*/

    BadgeBalance findByUserId(User user);
}

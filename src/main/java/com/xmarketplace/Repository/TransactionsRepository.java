package com.xmarketplace.Repository;

import com.xmarketplace.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

    @Query( value = "select * from transactions where user_id = ?1 and action = ?2 order by created_at desc limit 1",
    nativeQuery = true)
    Optional<Transactions> findLatestByUserIdAndAction(Integer userId, String action);

    @Query( value = "select * from transactions where user_id = ?1",nativeQuery = true)
    Optional<List<Transactions>> findAllByUserId(Integer id);
}
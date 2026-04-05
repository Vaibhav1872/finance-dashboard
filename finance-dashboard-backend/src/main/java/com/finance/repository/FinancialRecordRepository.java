package com.finance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finance.entity.FinancialRecord;
import com.finance.entity.User;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUser(User user);

    List<FinancialRecord> findByUserAndRecordType(User user, String recordType);

    List<FinancialRecord> findByUserAndCategory(User user, String category);

    List<FinancialRecord> findByUserAndRecordDateBetween(User user, LocalDate start, LocalDate end);

   
    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.recordType = 'INCOME'")
    Double getTotalIncome();

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.recordType = 'EXPENSE'")
    Double getTotalExpense();

  
    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r GROUP BY r.category")
    List<Object[]> getCategorySummary();

   
    List<FinancialRecord> findTop5ByOrderByRecordDateDesc();
}
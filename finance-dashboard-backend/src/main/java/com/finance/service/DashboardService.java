package com.finance.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.entity.FinancialRecord;
import com.finance.repository.FinancialRecordRepository;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository repo;

    public Map<String, Object> getSummary() {

        Double income = repo.getTotalIncome();
        Double expense = repo.getTotalExpense();

        Map<String, Object> data = new HashMap<>();
        data.put("income", income);
        data.put("expense", expense);
        data.put("balance", income - expense);

        return data;
    }

   
    public List<Map<String, Object>> getCategorySummary() {

        List<Object[]> rows = repo.getCategorySummary();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("total", row[1]);
            result.add(map);
        }

        return result;
    }

 
    public List<FinancialRecord> getRecentRecords() {
        return repo.findTop5ByOrderByRecordDateDesc();
    }
}
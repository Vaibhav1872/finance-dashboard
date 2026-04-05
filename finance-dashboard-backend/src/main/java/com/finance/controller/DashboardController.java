package com.finance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.entity.FinancialRecord;
import com.finance.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // ✅ SUMMARY (NO USER)
    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return dashboardService.getSummary();
    }

    // ✅ CATEGORY
    @GetMapping("/category")
    public List<Map<String, Object>> getCategorySummary() {
        return dashboardService.getCategorySummary();
    }

    // ✅ RECENT
    @GetMapping("/recent")
    public List<FinancialRecord> getRecent() {
        return dashboardService.getRecentRecords();
    }
}
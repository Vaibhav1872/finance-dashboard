package com.finance.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.finance.entity.FinancialRecord;
import com.finance.entity.User;
import com.finance.service.RecordService;
import com.finance.service.UserService;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FinancialRecord createRecord(
            @Valid @RequestBody FinancialRecord record,
            Principal principal) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        record.setUser(user);
        return recordService.saveRecord(record);
    }

   
    @GetMapping
    public List<FinancialRecord> getRecords(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return recordService.getUserRecords(user);
    }

   
    @PutMapping("/{id}")
    public FinancialRecord updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody FinancialRecord record) {

        return recordService.updateRecord(id, record);
    }

   
    @GetMapping("/filter/type")
    public List<FinancialRecord> filterByType(
            @RequestParam String type,
            Principal principal) {

        User user = userService.findByUsername(principal.getName()).get();
        return recordService.filterByType(user, type);
    }

    
    @GetMapping("/filter/category")
    public List<FinancialRecord> filterByCategory(
            @RequestParam String category,
            Principal principal) {

        User user = userService.findByUsername(principal.getName()).get();
        return recordService.filterByCategory(user, category);
    }

   
    @GetMapping("/filter/date")
    public List<FinancialRecord> filterByDate(
            @RequestParam String start,
            @RequestParam String end,
            Principal principal) {

        User user = userService.findByUsername(principal.getName()).get();

        return recordService.filterByDate(
                user,
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }

   
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }
}
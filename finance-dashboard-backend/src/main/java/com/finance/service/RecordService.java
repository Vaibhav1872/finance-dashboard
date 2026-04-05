package com.finance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.entity.FinancialRecord;
import com.finance.entity.User;
import com.finance.repository.FinancialRecordRepository;

@Service
public class RecordService {

    @Autowired
    private FinancialRecordRepository recordRepo;

    public FinancialRecord saveRecord(FinancialRecord record) {
        return recordRepo.save(record);
    }

    
    public List<FinancialRecord> getUserRecords(User user) {
        return recordRepo.findAll();
    }

    public FinancialRecord updateRecord(Long id, FinancialRecord updated) {

        FinancialRecord record = recordRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(updated.getAmount());
        record.setCategory(updated.getCategory());
        record.setRecordType(updated.getRecordType());
        record.setRecordDate(updated.getRecordDate());
        record.setNotes(updated.getNotes());

        return recordRepo.save(record);
    }

    public List<FinancialRecord> filterByType(User user, String type) {
        return recordRepo.findByUserAndRecordType(user, type);
    }

    public List<FinancialRecord> filterByCategory(User user, String category) {
        return recordRepo.findByUserAndCategory(user, category);
    }

    public List<FinancialRecord> filterByDate(User user, LocalDate start, LocalDate end) {
        return recordRepo.findByUserAndRecordDateBetween(user, start, end);
    }

    public void deleteRecord(Long id) {

        if (!recordRepo.existsById(id)) {
            throw new RuntimeException("Record not found");
        }

        recordRepo.deleteById(id);
    }
}
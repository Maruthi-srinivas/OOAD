package com.example.Telecom_buliding_system.service;

import com.example.Telecom_buliding_system.entity.Bill;
import com.example.Telecom_buliding_system.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final BillRepository billRepository;

    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }
}
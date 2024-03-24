package com.monitor.transaction.service.impl;

import com.monitor.transaction.model.entity.Customer;
import com.monitor.transaction.repository.CustomerRepository;
import com.monitor.transaction.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}

package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.entity.PaymentId;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final EntityMapper mapper;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(mapper::toPaymentDTO)
                .toList();
    }

    public List<PaymentDTO> getPaymentsByCustomer(Integer customerNumber) {
        return paymentRepository.findByCustomer_CustomerNumber(customerNumber)
                .stream()
                .map(mapper::toPaymentDTO)
                .toList();
    }

    public PaymentDTO getPaymentById(Integer customerNumber, String checkNumber) {
        PaymentId id = new PaymentId(customerNumber, checkNumber);
        return paymentRepository.findById(id)
                .map(mapper::toPaymentDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payment record not found."));
    }

    public PaymentDTO createPayment(Payment payment) {
        if (paymentRepository.existsById(payment.getId())) {
            throw new RuntimeException(
                    "Payment already exists with customerNumber: "
                            + payment.getId().getCustomerNumber()
                            + " and checkNumber: "
                            + payment.getId().getCheckNumber());
        }
        Customer customer = customerRepository
                .findById(payment.getId().getCustomerNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: "
                                + payment.getId().getCustomerNumber()));
        payment.setCustomer(customer);
        return mapper.toPaymentDTO(paymentRepository.save(payment));
    }

    public void deletePayment(Integer customerNumber, String checkNumber) {
        PaymentId id = new PaymentId(customerNumber, checkNumber);
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with customerNumber: "
                                + customerNumber
                                + " and checkNumber: "
                                + checkNumber));
        paymentRepository.delete(existing);
    }

    public PaymentDTO updatePayment(Integer customerNumber, String checkNumber, Payment updatedPayment) {
        PaymentId id = new PaymentId(customerNumber, checkNumber);
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with customerNumber: "
                                + customerNumber
                                + " and checkNumber: "
                                + checkNumber));
        
        existing.setPaymentDate(updatedPayment.getPaymentDate());
        existing.setAmount(updatedPayment.getAmount());
        
        return mapper.toPaymentDTO(paymentRepository.save(existing));
    }
}
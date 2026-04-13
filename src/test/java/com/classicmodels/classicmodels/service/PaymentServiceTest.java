package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.entity.PaymentId;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void getAllPayments_ShouldReturnList_WhenPaymentsExist() {
        // ARRANGE
        Payment payment = new Payment();
        PaymentDTO dto = new PaymentDTO();
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        when(mapper.toPaymentDTO(payment)).thenReturn(dto);

        // ACT
        List<PaymentDTO> result = paymentService.getAllPayments();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void getAllPayments_ShouldReturnEmptyList_WhenNoPaymentsExist() {
        // ARRANGE
        when(paymentRepository.findAll()).thenReturn(List.of());

        // ACT
        List<PaymentDTO> result = paymentService.getAllPayments();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void getPaymentsByCustomer_ShouldReturnList_WhenFound() {
        // ARRANGE
        Payment payment = new Payment();
        PaymentDTO dto = new PaymentDTO();
        when(paymentRepository.findByCustomer_CustomerNumber(101)).thenReturn(List.of(payment));
        when(mapper.toPaymentDTO(payment)).thenReturn(dto);

        // ACT
        List<PaymentDTO> result = paymentService.getPaymentsByCustomer(101);

        // ASSERT
        assertFalse(result.isEmpty());
        verify(paymentRepository, times(1)).findByCustomer_CustomerNumber(101);
    }

    @Test
    void createPayment_ShouldSaveAndReturnPayment_WhenValid() {
        // ARRANGE
        Payment payment = new Payment();
        payment.setId(new PaymentId(101, "CHK123"));
        PaymentDTO dto = new PaymentDTO();
        when(paymentRepository.existsById(payment.getId())).thenReturn(false);
        when(customerRepository.findById(101)).thenReturn(Optional.of(new Customer()));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(mapper.toPaymentDTO(payment)).thenReturn(dto);

        // ACT
        PaymentDTO result = paymentService.createPayment(payment);

        // ASSERT
        assertNotNull(result);
        verify(paymentRepository, times(1)).save(payment);
    }
    
    @Test
    void updatePayment_ShouldUpdateAndReturnPayment_WhenFound() {
        // ARRANGE
        Payment existing = new Payment();
        Payment updated = new Payment();
        PaymentDTO dto = new PaymentDTO();
        PaymentId id = new PaymentId(101, "CHK123");
        when(paymentRepository.findById(id)).thenReturn(Optional.of(existing));
        when(paymentRepository.save(existing)).thenReturn(existing);
        when(mapper.toPaymentDTO(existing)).thenReturn(dto);

        // ACT
        PaymentDTO result = paymentService.updatePayment(101, "CHK123", updated);

        // ASSERT
        assertNotNull(result);
        verify(paymentRepository, times(1)).save(existing);
    }

    @Test
    void updatePayment_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Payment updated = new Payment();
        PaymentId id = new PaymentId(101, "CHK123");
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(101, "CHK123", updated));
    }

    @Test
    void deletePayment_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Payment payment = new Payment();
        PaymentId id = new PaymentId(101, "CHK123");
        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        // ACT
        paymentService.deletePayment(101, "CHK123");

        // ASSERT
        verify(paymentRepository, times(1)).delete(payment);
    }

    @Test
    void deletePayment_ShouldThrowException_WhenMissing() {
        // ARRANGE
        PaymentId id = new PaymentId(101, "CHK123");
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> paymentService.deletePayment(101, "CHK123"));
    }
}
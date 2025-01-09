package org.example.project.service;

import org.example.project.dto.PaymentDto;
import org.example.project.repository.PaymentRepository;
import org.example.project.utils.InventoryStatus;
import org.example.project.utils.PaymentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.example.project.utils.PaymentUtil.fromDtoToPayment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

//    @Test
//    public void add10PaymentsTest() {
//        var payment = Payment
//                .builder()
//                .amount(2000)
//                .customer("Customer8")
//                .paymentType(PaymentType.ONLINE)
//                .paymentStatus(PaymentStatus.NEW)
//                .build();
//
//        when(paymentRepository.save(payment)).thenReturn(payment);
//
//        assertThrows(
//                RuntimeException.class,
//                () -> paymentService.add10Payments(),
//                "Something went wrong"
//        );
//    }


    @Test
    void testAddPaymentWithSuccess(){
        var paymentDto = PaymentDto
                .builder()
                .amount(2000)
                .customer("Customer8")
                .paymentType(PaymentType.POS)
                .paymentStatus(InventoryStatus.PROCESSED)
                .build();

        var payment = fromDtoToPayment(paymentDto);

        when(paymentRepository.save(payment)).thenReturn(payment);

        var result = paymentService.addPayment(paymentDto);

        assertEquals(paymentDto.getCustomer(), result.getCustomer());
        assertEquals(paymentDto.getAmount(), result.getAmount());
        verify(paymentRepository).save(payment);
    }

    @Test
    void testCancelPaymentWithSuccess(){
        var paymentId = 1;
        var paymentDto = PaymentDto
                .builder()
                .amount(2000)
                .customer("Customer8")
                .paymentType(PaymentType.POS)
                .paymentStatus(InventoryStatus.PROCESSED)
                .build();

        var payment = fromDtoToPayment(paymentDto);


        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        var result = paymentService.cancelPayment(paymentId);

        assertEquals(InventoryStatus.CANCELLED, result.getPaymentStatus());
        verify(paymentRepository).save(payment);
    }


    @Test
    void testCancelPaymentWithPaymentNotFound(){
        var paymentId = 1;
        var paymentDto = PaymentDto
                .builder()
                .amount(2000)
                .customer("Customer8")
                .paymentType(PaymentType.POS)
                .paymentStatus(InventoryStatus.PROCESSED)
                .build();

        var payment = fromDtoToPayment(paymentDto);


        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());
        when(paymentRepository.save(payment)).thenReturn(payment);

        assertThrows(
                RuntimeException.class,
                () -> paymentService.cancelPayment(paymentId),
                "The payment does not exist"
        );
    }


    @Test
    void testCancelPaymentWithPaymentAlreadyCancelled(){
        var paymentId = 1;
        var paymentDto = PaymentDto
                .builder()
                .amount(2000)
                .customer("Customer8")
                .paymentType(PaymentType.POS)
                .paymentStatus(InventoryStatus.CANCELLED)
                .build();

        var payment = fromDtoToPayment(paymentDto);


        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        assertThrows(
                RuntimeException.class,
                () -> paymentService.cancelPayment(paymentId),
                "Payment is already cancelled"
        );
    }
}

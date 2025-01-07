package org.example.project.utils;

import org.example.project.dto.PaymentDto;
import org.example.project.model.Payment;

public class PaymentUtil {

    public static Payment fromDtoToPayment(PaymentDto paymentDto) {
        return Payment.builder()
                .paymentStatus(paymentDto.getPaymentStatus())
                .paymentType(paymentDto.getPaymentType())
                .customer(paymentDto.getCustomer())
                .amount(paymentDto.getAmount())
                .build();
    }
}

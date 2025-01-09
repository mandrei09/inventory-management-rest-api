package org.example.project.service;

import org.example.project.dto.PaymentDto;
import org.example.project.exceptions.PaymentException;
import org.example.project.model.Payment;
import org.example.project.repository.PaymentRepository;
import org.example.project.utils.InventoryStatus;
import org.example.project.utils.PaymentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.example.project.utils.PaymentUtil.fromDtoToPayment;

@Service
//@Transactional
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

//    @Transactional
//    public void add10Payments() {
//        for (int i = 0; i < 10; i++) {
//            var payment = Payment
//                    .builder()
//                    .amount(2000)
//                    .customer("Customer " + i)
//                    .paymentType(PaymentType.ONLINE)
//                    .paymentStatus(PaymentStatus.NEW)
//                    .build();
//            if(i == 5) {
//                throw new RuntimeException("Something went wrong");
//            }
//            paymentRepository.save(payment);
//        }
//    }

    public Payment addPayment(PaymentDto payment) {
          var databasePayment =  paymentRepository.save(fromDtoToPayment(payment));
          LOGGER.debug("Payment {} was successfully saved in db.", databasePayment);
          return databasePayment;
    }

    public Payment cancelPayment(Integer paymentId) {
        var payment =  paymentRepository.findById(paymentId)
                .map(innerPayment -> {
                    if(innerPayment.getPaymentStatus() == InventoryStatus.CANCELLED) {
                        throw new PaymentException("Payment is already cancelled");
                    }
                    return innerPayment;
                })
                .orElseThrow(() -> new PaymentException("The payment does not exist"));

        payment.setPaymentStatus(InventoryStatus.CANCELLED);
        LOGGER.debug("Payment {} was successfully cancelled.", payment);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPayments(Map<String, String> filters) {
        List<Payment> payments;
        if (filters != null && !filters.isEmpty()) {
           payments = paymentRepository
                    .findAllByPaymentStatusOrPaymentType(InventoryStatus.getValueByString(filters.get("paymentStatus")),
                            PaymentType.getValueByString(filters.get("paymentType")));
           LOGGER.debug("Returning {} payments filtered by {}", payments, filters);
        } else {
            payments =  paymentRepository.findAll();
            LOGGER.debug("Returning {} payments", payments);
        }
        return payments;
    }

}

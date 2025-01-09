package org.example.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
//        Payment payment = Payment
//                .builder()
//                .amount(20000)
//                .customer("Customer 1")
//                .paymentType(PaymentType.ONLINE)
//                .paymentStatus(InventoryStatus.NEW)
//                .build();
//
//        Payment payment1 = Payment
//                .builder()
//                .amount(20000)
//                .customer("Customer 1")
//                .paymentType(PaymentType.ONLINE)
//                .paymentStatus(InventoryStatus.NEW)
//                .build();
//
//        Payment payment2 = Payment
//                .builder()
//                .amount(20000)
//                .customer("Customer 1")
//                .paymentType(PaymentType.POS)
//                .paymentStatus(InventoryStatus.CANCELLED)
//                .build();
//
//        Payment payment3 = Payment
//                .builder()
//                .amount(20000)
//                .customer("Customer 1")
//                .paymentType(PaymentType.POS)
//                .paymentStatus(InventoryStatus.PROCESSED)
//                .build();

//        paymentRepository.save(payment2);
//        paymentRepository.save(payment3);

//        paymentRepository.cancelPayment(PaymentStatus.CANCELLED, "Customer 1");



//        var paymets = paymentRepository.findAllByPaymentStatus(PaymentStatus.CANCELLED);
//        System.out.println(paymets);
//        paymentService.add10Payments();
    }

    public static void main(String[] args) {

        SpringApplication.run(ProjectApplication.class, args);
    }

}

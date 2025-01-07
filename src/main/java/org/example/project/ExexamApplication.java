package org.example.project;

import org.example.project.model.Payment;
import org.example.project.repository.PaymentRepository;
import org.example.project.service.PaymentService;
import org.example.project.utils.PaymentStatus;
import org.example.project.utils.PaymentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExexamApplication implements CommandLineRunner {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    /**
     * ACID => ATOMICA, CONSISTENTA, IZOLATA, DURABILA
     * @param paymentRepository
     * @param paymentService
     */

    public ExexamApplication(PaymentRepository paymentRepository, PaymentService paymentService) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @Override
    public void run(String... args) throws Exception {
        Payment payment = Payment
                .builder()
                .amount(20000)
                .customer("Customer 1")
                .paymentType(PaymentType.ONLINE)
                .paymentStatus(PaymentStatus.NEW)
                .build();

        Payment payment1 = Payment
                .builder()
                .amount(20000)
                .customer("Customer 1")
                .paymentType(PaymentType.ONLINE)
                .paymentStatus(PaymentStatus.NEW)
                .build();

        Payment payment2 = Payment
                .builder()
                .amount(20000)
                .customer("Customer 1")
                .paymentType(PaymentType.POS)
                .paymentStatus(PaymentStatus.CANCELLED)
                .build();

        Payment payment3 = Payment
                .builder()
                .amount(20000)
                .customer("Customer 1")
                .paymentType(PaymentType.POS)
                .paymentStatus(PaymentStatus.PROCESSED)
                .build();

//        paymentRepository.save(payment2);
//        paymentRepository.save(payment3);

//        paymentRepository.cancelPayment(PaymentStatus.CANCELLED, "Customer 1");



//        var paymets = paymentRepository.findAllByPaymentStatus(PaymentStatus.CANCELLED);
//        System.out.println(paymets);
//        paymentService.add10Payments();
    }

    public static void main(String[] args) {

        SpringApplication.run(ExexamApplication.class, args);
    }

}

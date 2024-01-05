package me.chulgil.msa.payment.adapter.in.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.payment.application.port.in.FinishSettlementCommand;
import me.chulgil.msa.payment.application.port.in.RequestPaymentCommand;
import me.chulgil.msa.payment.application.port.in.RequestPaymentUseCase;
import me.chulgil.msa.payment.domain.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

    private final RequestPaymentUseCase requestPaymentUseCase;

    @PostMapping(path = "/payment/request")
    void requestPayment(PaymentRequest request) {
        requestPaymentUseCase.requestPayment(RequestPaymentCommand.builder()
                                                     .requestMembershipId(request.getRequestMembershipId())
                                                     .requestPrice(request.getRequestPrice())
                                                     .franchiseId(request.getFranchiseId())
                                                     .franchiseFeeRate(request.getFranchiseFeeRate())
                                                     .build());
    }

    @GetMapping(path = "/payment/normal-status")
    List<Payment> getNormalStatusPayments() {
        return requestPaymentUseCase.getNormalStatusPayments();
    }

    @PostMapping(path = "/payment/finish-settlement")
    void finishSettlement(@RequestBody FinishSettlementRequest request) {
        System.out.println("request.getPaymentId() = " + request.getPaymentId());
        requestPaymentUseCase.finishPayment(new FinishSettlementCommand(request.getPaymentId()));
    }

}

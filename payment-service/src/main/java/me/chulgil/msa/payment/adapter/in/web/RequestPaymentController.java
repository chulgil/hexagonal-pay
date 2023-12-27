package me.chulgil.msa.payment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.payment.application.port.in.RequestPaymentCommand;
import me.chulgil.msa.payment.application.port.in.RequestPaymentUseCase;
import org.springframework.web.bind.annotation.PostMapping;
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

}

package me.chulgil.msa.payment.application.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.payment.application.port.in.RequestPaymentCommand;
import me.chulgil.msa.payment.application.port.in.RequestPaymentUseCase;
import me.chulgil.msa.payment.application.port.out.CreatePaymentPort;
import me.chulgil.msa.payment.application.port.out.GetMembershipPort;
import me.chulgil.msa.payment.application.port.out.GetRegisteredBankAccountPort;
import me.chulgil.msa.payment.domain.Payment;


@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements RequestPaymentUseCase {

    private final CreatePaymentPort createPaymentPort;

    private final GetMembershipPort getMembershipPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;


    @Override
    public Payment requestPayment(RequestPaymentCommand command) {

        // 충전도, 멤버십, 머니 유효성 확인.....
        // getMembershipPort.getMembership(command.getRequestMembershipId());

        //getRegisteredBankAccountPort.getRegisteredBankAccount(command.getRequestMembershipId());

        //....

        // createPaymentPort
        return createPaymentPort.createPayment(command.getRequestMembershipId(), command.getRequestPrice(),
                                               command.getFranchiseId(), command.getFranchiseFeeRate());
    }
}

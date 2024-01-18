package me.chulgil.msa.settlement.scheduler.job.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.settlement.adapter.out.service.Payment;
import me.chulgil.msa.settlement.port.out.GetRegisteredBankAccountPort;
import me.chulgil.msa.settlement.port.out.PaymentPort;
import me.chulgil.msa.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlementTasklet implements Tasklet {

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final PaymentPort paymentPort;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // 1. payment service 에서 결제 완료된 결제 내역을 조회한다.
        List<Payment> normalStatusPaymentList = paymentPort.getNormalStatusPayments();
        System.out.println("결제 완료된 내역 조회");
        for (Payment payment : normalStatusPaymentList) {
            System.out.println("id:" + payment.getPaymentId() + " price: " + payment.getRequestPrice());
        }

        // 2. 각 결제 내역의 franchiseId 에 해당하는 멤버십 정보(membershipId)에 대한
        // 뱅킹 정보(계좌번호) 를 가져와서
        Map<String, FirmbankingRequestInfo> franchiseIdToBankAccountMap = new HashMap<>();
        for (Payment payment : normalStatusPaymentList) {
            RegisteredBankAccountAggregateIdentifier entity = getRegisteredBankAccountPort.getRegisteredBankAccount(
                    payment.getFranchiseId());
            franchiseIdToBankAccountMap.put(payment.getFranchiseId(), FirmbankingRequestInfo.builder()
                    .bankName(entity.getBankName())
                    .bankAccountNumber(entity.getBankAccountNumber())
                    .build());
        }

        // 3. 각 franchiseId 별로, 정산 금액을 계산해주고
        // 수수료를 제하지 않았어요.
        for (Payment payment : normalStatusPaymentList) {
            FirmbankingRequestInfo firmbankingRequestInfo = franchiseIdToBankAccountMap.get(payment.getFranchiseId());
            double fee = Double.parseDouble(payment.getFranchiseFeeRate());
            int calculatedPrice = (int) ((100 - fee) * payment.getRequestPrice() * 100);
            firmbankingRequestInfo.setMoneyAmount(firmbankingRequestInfo.getMoneyAmount() + calculatedPrice);
        }

        // 4. 계산된 금액을 펌뱅킹 요청해주고
        for (FirmbankingRequestInfo firmbankingRequestInfo : franchiseIdToBankAccountMap.values()) {
            System.out.println("계산된 금액으로 펌뱅킹 요청 : " + firmbankingRequestInfo.getBankAccountNumber());
            System.out.println(" 금액 : " + firmbankingRequestInfo.getMoneyAmount());
            getRegisteredBankAccountPort.requestFirmbanking(firmbankingRequestInfo.getBankName(),
                                                            firmbankingRequestInfo.getBankAccountNumber(),
                                                            firmbankingRequestInfo.getMoneyAmount());
        }

        // 5. 정산 완료된 결제 내역은 정산 완료 상태로 변경해준다.
        for (Payment payment : normalStatusPaymentList) {
            System.out.println("정산 완료 처리 : " + payment.getPaymentId());
            paymentPort.finishSettlement(payment.getPaymentId());
        }

        return RepeatStatus.FINISHED;
    }
}

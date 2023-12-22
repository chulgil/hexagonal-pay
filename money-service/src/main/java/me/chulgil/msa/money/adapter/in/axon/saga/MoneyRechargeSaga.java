package me.chulgil.msa.money.adapter.in.axon.saga;

import java.util.UUID;
import lombok.NoArgsConstructor;
import me.chulgil.msa.common.event.CheckRegisteredBankAccountCommand;
import me.chulgil.msa.money.adapter.in.axon.event.RechargingRequestCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {

    @NotNull
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(@NotNull CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * 충전 동작을 시작하는 이벤트
     * <p>
     * associationProperty -> rechargingRequestId : 하나의 충전 요청에 대한 하나의 속성
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    public void handle(RechargingRequestCreatedEvent event) {
        System.out.println("RechargingRequestCreatedEvent Start saga");

        String rechargingRequestId = event.getRechargingRequestId();
        SagaLifecycle.associateWith("rechargingRequestId", rechargingRequestId);

        String checkRegisteredBankAccountId = UUID.randomUUID()
                                                  .toString();
        SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId);

        // "충전 요청" 이 시작 되었다.

        // 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccount)
        // CheckRegisteredBankAccountCommand -> Check Bank Account
        // -> axon server -> Banking Service -> Common
        commandGateway.send(CheckRegisteredBankAccountCommand.builder()
                              .aggregateIdentifier(event.getRegisteredBankAccountAggregateIdentifier())
                              .rechargeRequestId(event.getRechargingRequestId())
                              .membershipId(event.getMembershipId())
                              .checkRegisteredBankAccountId(checkRegisteredBankAccountId)
                              .bankName(event.getBankName())
                              .bankAccountNumber(event.getBankAccountNumber())
                              .amount(event.getAmount())
                              .build())
                      .whenComplete((result, throwable) -> {
                          if (throwable != null) {
                              throwable.printStackTrace();
                              System.out.println("CheckRegisteredBankAccountCommand Command failed");
                          } else {
                              System.out.println("CheckRegisteredBankAccountCommand Command success");
                          }
                      });
    }
}
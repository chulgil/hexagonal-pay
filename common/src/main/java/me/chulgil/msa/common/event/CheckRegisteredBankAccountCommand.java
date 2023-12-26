package me.chulgil.msa.common.event;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * RegisteredBankAccount.aggregateIdentifier가
 * 고객(MembershipId)에 대해서 정상적인 상황인지 판단하는 커맨드
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckRegisteredBankAccountCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier; // RegisteredBankAccount의 aggregateIdentifier
    private String rechargeRequestId; // 충전 요청 ID
    private String membershipId; // 고객의 ID

    private String checkRegisteredBankAccountId; // CheckRegisteredBankAccount의 ID
    private String bankName; // 은행 이름
    private String bankAccountNumber; // 은행 계좌 번호
    private int amount; // 충전 요청 금액
}

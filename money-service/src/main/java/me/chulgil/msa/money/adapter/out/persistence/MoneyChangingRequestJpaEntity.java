package me.chulgil.msa.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moneyChangingRequestId;

    private String targetMembershipId;

    private int moneyChangingType;  // 0: 증액, 1: 감액

    private int moneyAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    private int changingMoneyStatus; // 0:요청, 1: 성공, 2: 실패

    private String uuid;

    @Builder
    public MoneyChangingRequestJpaEntity(String targetMembershipId,
                                         int moneyChangingType,
                                         int moneyAmount,
                                         Date timeStamp,
                                         int changingMoneyStatus,
                                         String uuid) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.moneyAmount = moneyAmount;
        this.timeStamp = timeStamp;
        this.changingMoneyStatus = changingMoneyStatus;
        this.uuid = uuid;
    }


}

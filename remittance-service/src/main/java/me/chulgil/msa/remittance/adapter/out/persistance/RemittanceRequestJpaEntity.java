package me.chulgil.msa.remittance.adapter.out.persistance;

import javax.persistence.GenerationType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_remittance")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceRequestJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long remittanceRequestId;
    private String fromMembershipId; // from membership
    private String toMembershipId; // to membership
    private String toBankName;
    private String toBankAccountNumber;
    private int remittanceType; // 0: membership(내부 고객), 1: bank (외부 은행 계좌)
    // 송금요청 금액
    private int amount;

    @Setter
    private String remittanceStatus;

}

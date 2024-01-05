package me.chulgil.msa.settlement.scheduler.job.tasklet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FirmbankingRequestInfo {

    private String bankName;
    private String bankAccountNumber;
    @Setter
    private int moneyAmount;
}

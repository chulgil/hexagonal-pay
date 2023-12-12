package me.chulgil.msa.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargingMoneyTask {

    private String taskId;

    private String taskName;

    private String membershipId;

    private List<SubTask> subTasks;

    // 법인 계좌 은행
    private String toBankName;

    // 법인 계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount;

}

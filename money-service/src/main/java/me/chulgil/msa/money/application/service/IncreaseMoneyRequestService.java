package me.chulgil.msa.money.application.service;

import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.CountDownLatchManager;
import me.chulgil.msa.common.RechargingMoneyTask;
import me.chulgil.msa.common.SubTask;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.chulgil.msa.money.adapter.out.persistence.MoneyChangingRequestMapper;
import me.chulgil.msa.money.application.port.in.CreateMemberMoneyCommand;
import me.chulgil.msa.money.application.port.in.CreateMemberMoneyUseCase;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestCommand;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestUseCase;
import me.chulgil.msa.money.application.port.out.GetMembershipPort;
import me.chulgil.msa.money.application.port.out.IncreaseMoneyPort;
import me.chulgil.msa.money.application.port.out.SendRechargingMoneyTaskPort;
import me.chulgil.msa.money.domain.MemberMoney;
import me.chulgil.msa.money.domain.MoneyChangingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;

    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    private final GetMembershipPort membershipPort;

    private final IncreaseMoneyPort increaseMoneyPort;

    private final MoneyChangingRequestMapper mapper;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 충전.증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴

        // 성공 시에 멤버의 MemberMoney 값 증액이 필요
        return getMoneyChangingRequest(command);
    }

    @Nullable
    private MoneyChangingRequest getMoneyChangingRequest(IncreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity entity = increaseMoneyPort.increaseMemberMoney(
            new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

        if (entity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(1),
                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                new MoneyChangingRequest.MoneyChangingStatus(1), new MoneyChangingRequest.Uuid(UUID.randomUUID()
                                                                                                   .toString())
            ));
        }

        // 6-2. 결과가 정상적이 아니라면. 실패로 MoneyChangingRequest 상태값을 변동 후에 리턴
        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // Subtask : 각 서비스에 특정 membershipId로 Validation 을 하기 위한 Task

        // 1. Subtask 를 생성한다. (Subtask)
        SubTask validMembershipTask = SubTask.builder()
            .subTaskName("validMembershipTask : " + "멤버십 유효성 검사")
            .membershipId(command.getTargetMembershipId())
            .taskType("membership")
            .status("ready")
            .build();

        // Banking Account Validation
        // Amount Money Firmbanking --> 무조건 받았다고 가정
        SubTask validBankingAccountTask = SubTask.builder()
            .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
            .membershipId(command.getTargetMembershipId())
            .taskType("banking")
            .status("ready")
            .build();

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(validMembershipTask);
        subTasks.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
            .taskId(UUID.randomUUID()
                        .toString())
            .taskName("RechargingMoneyTask : " + "머니 증액")
            .membershipId(command.getTargetMembershipId())
            .subTasks(subTasks)
            .toBankName("CG페이")
            .toBankAccountNumber("1234-1234-1234")
            .moneyAmount(command.getAmount())
            .build();

        // 2. Kafka Cluster Produce
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(task);
        countDownLatchManager.addCountDownLatch(task.getTaskId());

        // 3. Wait ...
        try {
            // Task 완료 이벤트 올 때까지 기다린다.
            countDownLatchManager.getCountDownLatch(task.getTaskId())
                                 .await();

            // 3-1. task-consumer
            // 4. Task Result Consumer
            // 받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 가져온다.
            String result = countDownLatchManager.getDataForKey(task.getTaskId());
            if (result.equals("success")) {
                // 5. Consume ok, Logic 제대로 수행됨,, 머니 증액.
                System.out.println("success for async Money Recharging!!");
                return getMoneyChangingRequest(command);
            } else {
                // 5. Consume fail, Logic
                return null;
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {

    }
}

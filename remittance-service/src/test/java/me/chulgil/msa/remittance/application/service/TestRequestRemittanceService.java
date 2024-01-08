package me.chulgil.msa.remittance.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestMapper;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceCommand;
import me.chulgil.msa.remittance.application.port.out.RequestRemittancePort;
import me.chulgil.msa.remittance.application.port.out.banking.BankingPort;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipPort;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipStatus;
import me.chulgil.msa.remittance.application.port.out.money.MoneyInfo;
import me.chulgil.msa.remittance.application.port.out.money.MoneyPort;
import me.chulgil.msa.remittance.domain.RemittanceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRequestRemittanceService {

    // Inject
    // @InjectMocks
    @InjectMocks
    private RequestRemittanceService requestRemittanceService;

    @Mock
    private RequestRemittancePort remittancePort;
    @Mock
    private RemittanceRequestMapper mapper;
    @Mock
    private MembershipPort membershipPort;
    @Mock
    private MoneyPort moneyPort;
    @Mock
    private BankingPort bankingPort;

    @BeforeEach
    public void setUp() {
        // InjectMocks 가 포함된 클래스에 @Mock 을 주입
        MockitoAnnotations.openMocks(this);

        // private final의 경우 Reflection or Constructor 을 통해 주입
        requestRemittanceService = new RequestRemittanceService(remittancePort, mapper, membershipPort, moneyPort,
                                                                bankingPort);
    }

    private static Stream<RequestRemittanceCommand> provideRequestRemittanceCommand() {
        return Stream.of(RequestRemittanceCommand.builder()
                                 .fromMembershipId("fromMembershipId")
                                 .toMembershipId("toMembershipId")
                                 .amount(10000)
                                 .remittanceType(0)
                                 .build(), RequestRemittanceCommand.builder()
                                 .fromMembershipId("fromMembershipId")
                                 .toMembershipId("toMembershipId")
                                 .amount(10000)
                                 .remittanceType(1)
                                 .build());
    }

    // // 송금 요청을 한 고객이 유효하지 않은 경우
    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceService(RequestRemittanceCommand testCommand) {
        System.out.println("requestRemittanceService = " + requestRemittanceService);

        // 1. 먼저, 어떤 결과가 나올 것인지 정의
        RemittanceRequest want = null;

        // 2. Mocking을 위한 dummy data 생성
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(MembershipStatus.builder()
                                    .membershipId(testCommand.getFromMembershipId())
                                    .isValid(false)
                                    .build());

        // 3. 결과를 위해 Mocking

        // 4. mock을 사용해서, 테스트
        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        // 5. Verify 를 통해서, 테스트 진행확인
        verify(remittancePort, times(1)).createHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());

        // 6. 테스트 결과 확인
        assertEquals(want, got);
    }

    // 잔액이 충분하지 않음
    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceServiceWhenNotEnoughMoney(RequestRemittanceCommand testCommand) {
        // 1. 먼저, 어떤 결과가 나올 것인지 정의
        RemittanceRequest want = null;

        // 2. Mocking을 위한 dummy data 생성
        MoneyInfo dummyMoneyInfo = MoneyInfo.builder()
                .membershipId(testCommand.getFromMembershipId())
                .balance(10000)
                .build();

        // 3. 결과를 위해 Mocking
        when(remittancePort.createHistory(testCommand))
                .thenReturn(null);
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(MembershipStatus.builder()
                                    .membershipId(testCommand.getFromMembershipId())
                                    .isValid(true)
                                    .build());
        when(moneyPort.getMoneyInfo(testCommand.getFromMembershipId()))
                .thenReturn(dummyMoneyInfo);

        int chargedAmount = (int) Math.ceil((testCommand.getAmount() - dummyMoneyInfo.getBalance()) / 10000.0) * 10000;
        when(moneyPort.requestMoneyRecharging(testCommand.getFromMembershipId(), chargedAmount))
                .thenReturn(true);

        // 4. mock을 사용해서, 테스트
        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        // 5. Verify 를 통해서, 테스트 진행확인
        verify(remittancePort, times(1)).createHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());

        // 6. 테스트 결과 확인
        assertEquals(want, got);
    }


}

package me.chulgil.msa.banking.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.chulgil.msa.banking.adapter.out.external.bank.FirmbankingResult;
import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestMapper;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingUseCase;
import me.chulgil.msa.banking.application.port.out.RequestExternalFirmbankingPort;
import me.chulgil.msa.banking.application.port.out.RequestFirmbankingPort;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;
    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;


    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

        // Business Logic
        // a -> b 계좌

        // 1. 요청에 대해 정보를 먼저 write . "요청" 상태로
        FirmbankingRequestJpaEntity entity = requestFirmbankingPort.createFirmbankingRequest(
            new FirmbankingRequest.FromBankName(command.getFromBankName()),
            new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
            new FirmbankingRequest.ToBankName(command.getToBankName()),
            new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
            new FirmbankingRequest.FirmbankingStatus(0)
        );

        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(
            new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        // Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        entity.setUuid(randomUUID.toString());

        // 3. 결과에 따라서 1번에서 만든 정보를 Update
        if(result.getResultCode() == 0) {
            // 성공
            entity.setFirmbankingStatus(1);
        } else {
            // 실패
            entity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴 전 변경된 상태 값을 기준으로 다시 저장
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(entity), randomUUID);
    }
}

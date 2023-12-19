package me.chulgil.msa.banking.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.chulgil.msa.banking.adapter.out.external.bank.FirmbankingResult;
import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestMapper;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingUseCase;
import me.chulgil.msa.banking.application.port.in.UpdateFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.UpdateFirmbankingUseCase;
import me.chulgil.msa.banking.application.port.out.RequestExternalFirmbankingPort;
import me.chulgil.msa.banking.application.port.out.RequestFirmbankingPort;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.UseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final CommandGateway commandGateway;

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
            new FirmbankingRequest.FirmbankingStatus(0),
            new FirmbankingRequest.FirmbankingAggregateIdentifier("")
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
        if (result.getResultCode() == 0) {
            // 성공
            entity.setFirmbankingStatus(1);
        } else {
            // 실패
            entity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴 전 변경된 상태 값을 기준으로 다시 저장
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(entity), randomUUID);
    }

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingCommand command) {
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
            .fromBankName(command.getFromBankName())
            .fromBankAccountNumber(command.getFromBankAccountNumber())
            .toBankName(command.getToBankName())
            .toBankAccountNumber(command.getToBankAccountNumber())
            .moneyAmount(command.getMoneyAmount())
            .build();
        commandGateway.send(createFirmbankingRequestCommand)
            .whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    } else {
                        System.out.println(
                            "createFirmbankingRequestCommand success, AggregateId : " + result.toString());

                        // Request Firmbanking 의 DB save
                        FirmbankingRequestJpaEntity requestEntity = requestFirmbankingPort.createFirmbankingRequest(
                            new FirmbankingRequest.FromBankName(command.getFromBankName()),
                            new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                            new FirmbankingRequest.ToBankName(command.getToBankName()),
                            new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                            new FirmbankingRequest.FirmbankingStatus(0),
                            new FirmbankingRequest.FirmbankingAggregateIdentifier(result.toString())
                        );

                        // 은행에 펌뱅킹 요청
                        FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(
                            ExternalFirmbankingRequest.builder()
                                .fromBankName(command.getFromBankName())
                                .fromBankAccountNumber(command.getFromBankAccountNumber())
                                .toBankName(command.getToBankName())
                                .toBankAccountNumber(command.getToBankAccountNumber())
                                .build()
                        );

                        if (firmbankingResult.getResultCode() == 0) {
                            // 성공
                            requestEntity.setFirmbankingStatus(1);
                        } else {
                            // 실패
                            requestEntity.setFirmbankingStatus(2);
                        }
                        requestFirmbankingPort.modifyFirmbankingRequest(requestEntity);

                    }
                });
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
        UpdateFirmbankingRequestCommand updateCommand = UpdateFirmbankingRequestCommand.builder()
            .aggregateIdentifier(command.getFirmbankingAggregateIdentifier())
            .firmbankingStatus(command.getFirmbankingStatus())
            .build();
        commandGateway.send(updateCommand)
            .whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    } else {
                        System.out.println(
                            "updateFirmbankingRequestCommand completed, Aggregate ID: " + result.toString());
                        FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                            new FirmbankingRequest.FirmbankingAggregateIdentifier(result.toString()));
                        // status 의 변경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0, status 변경도 해주겠지만
                        // + 기존 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청
                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);
                    }
                }
            );
    }
}

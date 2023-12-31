package me.chulgil.msa.membership.application.service;

import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.membership.application.port.in.RegisterMembershipCommand;
import me.chulgil.msa.membership.application.port.in.RegisterMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.RegisterMembershipPort;
import me.chulgil.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RegisterMembership implements RegisterMembershipUseCase {

	private final RegisterMembershipPort rport;
	private final CommandGateway commandGateway;

	@Override
	public void registerMembership(RegisterMembershipCommand command) {
		rport.createMembership(
				new Membership.MembershipName(command.getName()),
				new Membership.MembershipEmail(command.getEmail()),
				new Membership.MembershipAddress(command.getAddress()),
				new Membership.MembershipIsValid(command.isValid()),
				new Membership.MembershipAggregateIdentifier("default")
				);
	}
	@Override
	public void registerAxonMembership(RegisterMembershipCommand command) {
		commandGateway.send(command)
			.whenComplete((Object result, Throwable throwable) -> {
				if (throwable == null) {
					System.out.println("Aggregate ID:" + result.toString());
					rport.createMembership(
							new Membership.MembershipName(command.getName()),
							new Membership.MembershipEmail(command.getEmail()),
							new Membership.MembershipAddress(command.getAddress()),
							new Membership.MembershipIsValid(command.isValid()),
							new Membership.MembershipAggregateIdentifier(result.toString())
					);
				}
				else{
					System.out.println("error : " + throwable.getMessage());
				}
			});
	}
}





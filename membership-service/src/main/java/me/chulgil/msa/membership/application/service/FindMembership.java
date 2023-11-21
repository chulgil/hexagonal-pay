package me.chulgil.msa.membership.application.service;

import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.membership.application.port.in.FindMembershipCommand;
import me.chulgil.msa.membership.application.port.in.FindMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.FindMembershipPort;
import me.chulgil.msa.membership.application.port.out.query.FindMembershipQuery;
import me.chulgil.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembership implements FindMembershipUseCase {

	private final FindMembershipPort fport;

	private final QueryGateway queryGateway;
	@Override
	public Membership findMembership(FindMembershipCommand command) {
		return fport.findMembership(new Membership.MembershipId(command.getMembershipId()));
	}

	@Override
	public Membership findAxonMembership(FindMembershipCommand command) {
		FindMembershipQuery getQuery = new FindMembershipQuery(command.getMembershipId());
		queryGateway.query(getQuery, ResponseTypes.instanceOf(Membership.class))
				.whenComplete((Object result, Throwable throwable) -> {
					if (throwable == null) {
						System.out.println(result.toString());
					}
					else {
						System.out.println("error : " + throwable.getMessage());
					}
				});
		return null;
	}
}





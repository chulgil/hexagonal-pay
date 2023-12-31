package me.chulgil.msa.membership.adapter.in.web;

import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.membership.application.port.in.FindMembershipCommand;
import me.chulgil.msa.membership.application.port.in.FindMembershipUseCase;
import me.chulgil.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class FindMembershipController {

	private final FindMembershipUseCase findMembershipUseCase;

	@GetMapping(path = "/membership/{membershipId}")
	ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId){

		FindMembershipCommand command = FindMembershipCommand.builder()
				.membershipId(membershipId)
				.build();
		return ResponseEntity.ok(findMembershipUseCase.findMembership(command));
	}

	@GetMapping(path = "/membership/axon/{membershipId}")
	ResponseEntity<Membership> findAxonMembershipByMemberId(@PathVariable String membershipId){

		FindMembershipCommand command = FindMembershipCommand.builder()
				.membershipId(membershipId)
				.build();

		return ResponseEntity.ok(findMembershipUseCase.findAxonMembership(command));
	}
}

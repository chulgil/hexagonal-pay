package me.chulgil.msa.membership.adapter.in.web;

import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.membership.application.port.in.UpdateMembershipCommand;
import me.chulgil.msa.membership.application.port.in.UpdateMembershipUseCase;
import me.chulgil.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class UpdateMembershipController {

	private final UpdateMembershipUseCase updateMembershipUseCase;
	@PutMapping(path = "/membership/update")
	ResponseEntity<Membership> updateMembershipByMemberId(@RequestBody UpdateMembershipRequest membershipToBeUpdated){
		// updateMembership
		UpdateMembershipCommand command = UpdateMembershipCommand.builder()
				.membershipId(membershipToBeUpdated.getMembershipId())
				.name(membershipToBeUpdated.getName())
				.address(membershipToBeUpdated.getAddress())
				.email(membershipToBeUpdated.getEmail())
				.isValid(membershipToBeUpdated.isValid())
				.build();
		return ResponseEntity.ok(updateMembershipUseCase.updateMembership(command));
	}

	@PutMapping(path = "/membership/axon-update")
	ResponseEntity<Membership> updateAxonMembershipByMemberId(@RequestBody UpdateMembershipRequest membershipToBeUpdated){
		System.out.println("axon update controller");
		// updateMembership
		UpdateMembershipCommand command = UpdateMembershipCommand.builder()
				.membershipId(membershipToBeUpdated.getMembershipId())
				.name(membershipToBeUpdated.getName())
				.address(membershipToBeUpdated.getAddress())
				.email(membershipToBeUpdated.getEmail())
				.isValid(membershipToBeUpdated.isValid())
				.build();
		return ResponseEntity.ok(updateMembershipUseCase.updateAxonMembership(command));
	}
}

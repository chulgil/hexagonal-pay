package me.chulgil.msa.membership.application.port.in;

public interface RegisterMembershipUseCase {

	void registerMembership(RegisterMembershipCommand command);
	void registerAxonMembership(RegisterMembershipCommand command);
}

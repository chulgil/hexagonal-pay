package me.chulgil.msa.remittance.adapter.out.service.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.common.ExternalSystemAdapter;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipPort;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements MembershipPort {

    private final CommonHttpClient httpClient;

    @Value("${service.membership.url}")
    private String membershipServiceEndpoint;

    @Override
    public MembershipStatus getMembershipStatus(String membershipId) {
        String buildUrl = String.join("/", this.membershipServiceEndpoint, "membership", membershipId);
        try {
            String jsonResponse = httpClient.sendGetRequest(buildUrl).body();
            ObjectMapper mapper = new ObjectMapper();
            Membership mem = mapper.readValue(jsonResponse, Membership.class);
            if (mem.isValid()) {
                return new MembershipStatus(mem.getMembershipId(), true);
            } else {
                return new MembershipStatus(mem.getMembershipId(), false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

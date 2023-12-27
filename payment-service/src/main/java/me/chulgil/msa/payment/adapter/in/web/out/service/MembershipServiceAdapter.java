package me.chulgil.msa.payment.adapter.in.web.out.service;

import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.payment.application.port.out.GetMembershipPort;
import me.chulgil.msa.payment.application.port.out.MembershipStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient httpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient httpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.httpClient = httpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {

        String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();
            // json Membership

            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid()) {
                return MembershipStatus.builder()
                        .membershipId(membership.getMembershipId())
                        .isValid(true)
                        .build();
            } else {
                return MembershipStatus.builder()
                        .membershipId(membership.getMembershipId())
                        .isValid(false)
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

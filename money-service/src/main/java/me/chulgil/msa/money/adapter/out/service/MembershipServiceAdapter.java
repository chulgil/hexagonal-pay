package me.chulgil.msa.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.money.application.port.out.GetMembershipPort;
import me.chulgil.msa.money.application.port.out.MembershipStatus;
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

        // Http client를 통해 Membership 서비스에 요청
        String url = String.join(
            "/",
            membershipServiceUrl,
            "membership",
            membershipId
        );
        try {
            String jsonResponse = httpClient.sendGetRequest(url).body();
            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(
                jsonResponse,
                Membership.class
            );

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

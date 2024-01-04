package me.chulgil.msa.money.query.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.money.query.application.port.out.GetMemberAddressInfoPort;
import me.chulgil.msa.money.query.application.port.out.MemberAddressInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMemberAddressInfoPort {

    private final CommonHttpClient httpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient httpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.httpClient = httpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MemberAddressInfo getMemberAddressInfo(String membershipId) {

        String url = String.join("/", membershipServiceUrl, "membership", membershipId);

        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();

            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            return MemberAddressInfo.builder()
                    .membershipId(membership.getMembershipId())
                    .address(membership.getAddress())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

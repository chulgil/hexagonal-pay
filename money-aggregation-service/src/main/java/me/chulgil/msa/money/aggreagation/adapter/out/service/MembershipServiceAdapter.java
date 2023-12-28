package me.chulgil.msa.money.aggreagation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.common.ExternalSystemAdapter;
import me.chulgil.msa.money.aggreagation.application.port.out.GetMembershipPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient httpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient httpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.httpClient = httpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public List<String> getMembershipByAddress(String address) {
        String url = String.join("/", membershipServiceUrl, "membership/address", address);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();
            // json Membership
            List<Membership> membershipList = mapper.readValue(jsonResponse, new TypeReference<>() {
            });
            List<String> membershipIds = new ArrayList<>();
            for (Membership membership : membershipList) {
                String membershipId = membership.getMembershipId();
                membershipIds.add(membershipId);
            }

            return membershipIds;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

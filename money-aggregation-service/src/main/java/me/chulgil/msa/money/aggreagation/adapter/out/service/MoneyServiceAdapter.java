package me.chulgil.msa.money.aggreagation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.common.ExternalSystemAdapter;
import me.chulgil.msa.money.aggreagation.application.port.out.GetMoneySumPort;
import me.chulgil.msa.money.aggreagation.application.port.out.MemberMoney;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
public class MoneyServiceAdapter implements GetMoneySumPort {

    private final CommonHttpClient httpClient;

    private final String moneyServiceUrl;

    public MoneyServiceAdapter(CommonHttpClient httpClient,
                               @Value("${service.money.url}") String moneyServiceUrl) {
        this.httpClient = httpClient;
        this.moneyServiceUrl = moneyServiceUrl;
    }

    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
        String url = String.join("/", moneyServiceUrl, "money/member-money");
        ObjectMapper mapper = new ObjectMapper();
        try {
            GetMemberMoneyRequest request = GetMemberMoneyRequest.builder()
                    .membershipIds(membershipIds)
                    .build();
            String jsonResponse = httpClient.sendPostRequest(url, mapper.writeValueAsString(request)).get()
                                            .body();
            return mapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

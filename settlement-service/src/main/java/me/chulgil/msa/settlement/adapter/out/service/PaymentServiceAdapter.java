package me.chulgil.msa.settlement.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.settlement.port.out.PaymentPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceAdapter implements PaymentPort {

    private static final String NORMAL_STATUS_PATH = "payment/normal-status";
    private final CommonHttpClient httpClient;

    private final String paymentServiceUrl;

    public PaymentServiceAdapter(CommonHttpClient httpClient,
                                 @Value("${service.payment.url}") String paymentServiceUrl) {
        this.httpClient = httpClient;
        this.paymentServiceUrl = paymentServiceUrl;
    }


    @Override
    public List<Payment> getNormalStatusPayments() {
        String url = String.join("/", paymentServiceUrl, NORMAL_STATUS_PATH);
        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();
            ObjectMapper mapper = new ObjectMapper();
            List<Payment> paymentList = mapper.readValue(jsonResponse, new TypeReference<>() {});
            return paymentList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finishSettlement(Long paymentId) {
        String url = String.join("/", paymentServiceUrl, "payment/finish-settlement");
        ObjectMapper mapper = new ObjectMapper();

        try {
            FinishSettlementRequest request = FinishSettlementRequest.builder()
                    .paymentId(String.valueOf(paymentId))
                    .build();
            String result = httpClient.sendPostRequest(url, mapper.writeValueAsString(request))
                                    .get()
                                    .body();
            System.out.println("finishSettlement result: " + result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

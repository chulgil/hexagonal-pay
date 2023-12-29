package me.chulgil.msa.money.query.adapter.out.aws.dynamodb;

import java.util.Map;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class MoneySumByAddressMapper {
    public MoneySumByAddress mapToMoneySumByAddress(Map<String, AttributeValue> item) {
        return new MoneySumByAddress(
                item.get("PK").s(),
                item.get("SK").s(),
                Integer.parseInt(item.get("balance").n())
        );
    }
}

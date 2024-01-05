package me.chulgil.msa.money.query.adapter.out.aws.dynamodb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.chulgil.msa.money.query.adapter.axon.QueryMoneySumByAddress;
import me.chulgil.msa.money.query.application.port.out.GetMoneySumByAddressPort;
import me.chulgil.msa.money.query.application.port.out.GetMoneySumByRegionPort;
import me.chulgil.msa.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import me.chulgil.msa.money.query.domain.MoneySumByRegion;
import me.chulgil.msa.money.query.domain.MoneySumByRegion.MoneySum;
import me.chulgil.msa.money.query.domain.MoneySumByRegion.MoneySumByRegionId;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.ComparisonOperator;
import software.amazon.awssdk.services.dynamodb.model.Condition;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

@Component
public class DynamoDBAdapter implements GetMoneySumByAddressPort, GetMoneySumByRegionPort,
        InsertMoneyIncreaseEventByAddress {

    private final String tableName;

    private final DynamoDbClient dynamoDbClient;
    private final MoneySumByAddressMapper moneySumByAddressMapper;

    public DynamoDBAdapter(@Value("${aws.dynamodb.table}") String tableName,
                           @Value("${aws.dynamodb.access.key}") String accessKey,
                           @Value("${aws.dynamodb.secret.key}") String secretKey,
                           @Value("${aws.dynamodb.region}") String region) {

        this.tableName = tableName;
        this.moneySumByAddressMapper = new MoneySumByAddressMapper();

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public MoneySum getMoneySumByRegionPort(String regionName, Date startDate) {
        return null;
    }

    @Override
    public void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease) {
        // 1. raw event insert
        // PK: 강남구#231228 SK: 5,000
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String yyMMdd = dateFormat.format(new Date());
        String pk = addressName + "#" + yyMMdd;
        String sk = String.valueOf(moneyIncrease);
        putItem(pk, sk, moneyIncrease);

        // 2. 지역정보 잔액증가
        // 2-1. PK: 강남구#231228 SK: [[timestamp]] balance: + 5,000
        // PK: 강남구 balance: + 5,000
        String summaryPk = pk + "#summary";
        String summarySk = "-1";
        MoneySumByAddress moneySumByAddress = getItem(summaryPk, summarySk);
        if (moneySumByAddress == null) {
            putItem(summaryPk, summarySk, moneyIncrease);
        } else {
            int balance = moneySumByAddress.getBalance();
            balance += moneyIncrease;
            updateItem(summaryPk, summarySk, balance);
        }

        // 2-2. 지역별 정보
        // PK: 강남구 SK: -1 balance: + 5,000
        String summaryPk2 = addressName;
        String summarySk2 = "-1";
        MoneySumByAddress moneySumByAddress2 = getItem(summaryPk2, summarySk2);
        if (moneySumByAddress2 == null) {
            putItem(summaryPk2, summarySk2, moneyIncrease);
        } else {
            int balance2 = moneySumByAddress2.getBalance();
            balance2 += moneyIncrease;
            updateItem(summaryPk2, summarySk2, balance2);
        }
    }

    private void putItem(String pk, String sk, int balance) {
        try {
            String balanceStr = String.valueOf(balance);
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder()
                    .s(pk)
                    .build());
            attrMap.put("SK", AttributeValue.builder()
                    .s(sk)
                    .build());
            attrMap.put("balance", AttributeValue.builder()
                    .n(balanceStr)
                    .build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(this.tableName)
                    .item(attrMap)
                    .build();

            dynamoDbClient.putItem(request);
        } catch (DynamoDbException e) {
            System.err.println("Error adding an item to the table: " + e.getMessage());
        }
    }

    private MoneySumByAddress getItem(String pk, String sk) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder()
                    .s(pk)
                    .build());
            attrMap.put("SK", AttributeValue.builder()
                    .s(sk)
                    .build());

            GetItemRequest request = GetItemRequest.builder()
                    .tableName(this.tableName)
                    .key(attrMap)
                    .build();

            GetItemResponse response = dynamoDbClient.getItem(request);
            if (response.hasItem()) {
                return moneySumByAddressMapper.mapToMoneySumByAddress(response.item());
            } else {
                return null;
            }

        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }
        return null;
    }

    private void queryItem(String id) {
        try {
            // PK 만 써도 돼요.
            HashMap<String, Condition> attrMap = new HashMap<>();
            attrMap.put("PK", Condition.builder()
                    .attributeValueList(AttributeValue.builder()
                                                .s(id)
                                                .build())
                    .comparisonOperator(ComparisonOperator.EQ)
                    .build());

            QueryRequest request = QueryRequest.builder()
                    .tableName(this.tableName)
                    .keyConditions(attrMap)
                    .build();

            QueryResponse response = dynamoDbClient.query(request);
            response.items()
                    .forEach((value) -> System.out.println(value));
        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }
    }

    private void updateItem(String pk, String sk, int balance) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder()
                    .s(pk)
                    .build());
            attrMap.put("SK", AttributeValue.builder()
                    .s(sk)
                    .build());

            String balanceStr = String.valueOf(balance);
            // Create an UpdateItemRequest
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName(this.tableName)
                    .key(attrMap)
                    .attributeUpdates(new HashMap<String, AttributeValueUpdate>() {{
                        put("balance", AttributeValueUpdate.builder()
                                .value(AttributeValue.builder()
                                               .n(balanceStr)
                                               .build())
                                .action(AttributeAction.PUT)
                                .build());
                    }})
                    .build();

            UpdateItemResponse response = dynamoDbClient.updateItem(updateItemRequest);

            // 결과 출력.
            Map<String, AttributeValue> attributes = response.attributes();
            if (attributes != null) {
                for (Map.Entry<String, AttributeValue> entry : attributes.entrySet()) {
                    String attributeName = entry.getKey();
                    AttributeValue attributeValue = entry.getValue();
                    System.out.println(attributeName + ": " + attributeValue);
                }
            } else {
                System.out.println("Item was updated, but no attributes were returned.");
            }
        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }
    }


    @Override
    public int getMoneySumByAddress(String addressName) {
        String pk = addressName;
        String sk = "-1";
        return getItem(pk, sk).getBalance();
    }

    @QueryHandler
    public MoneySumByRegion query(QueryMoneySumByAddress query) {
        return MoneySumByRegion.generateMoneySumByRegion(new MoneySumByRegionId(UUID.randomUUID()
                                                                                    .toString()),
                                                         new MoneySumByRegion.RegionName(query.getAddress()),
                                                         new MoneySumByRegion.MoneySum(getMoneySumByAddress(query.getAddress())));
    }
}

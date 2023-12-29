package me.chulgil.msa.money.query.adapter.out.aws.dynamodb;

import java.util.Date;
import me.chulgil.msa.money.query.application.port.out.GetMoneySumByRegionPort;
import me.chulgil.msa.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import me.chulgil.msa.money.query.domain.MoneySumByRegion.MoneySum;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBAdapter implements GetMoneySumByRegionPort, InsertMoneyIncreaseEventByAddress {

    private final String tableName;
    private final String accessKey;
    private final String secretKey;

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBAdapter(@Value("${aws.dynamodb.table-name}") String tableName,
                           @Value("${aws.dynamodb.access-key}") String accessKey,
                           @Value("${aws.dynamodb.secret-key}") String secretKey) {

        this.tableName = tableName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public MoneySum getMoneySumByRegionPort(String regionName, Date startDate) {
        return null;
    }

    @Override
    public void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease) {

    }
}

package me.chulgil.msa.payment;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PaymentConfigurationProperties.class)
public class PaymentConfiguration {

}

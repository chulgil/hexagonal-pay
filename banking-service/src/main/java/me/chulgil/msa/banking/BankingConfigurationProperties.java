package me.chulgil.msa.banking;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "banking")
public class BankingConfigurationProperties {

  private long transferThreshold = Long.MAX_VALUE;

}

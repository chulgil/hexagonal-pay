package me.chulgil.msa.money.query.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneySumByRegion {

    private final String moneySumByRegionId;
    private final String regionName;
    private final long moneySum;

    public static MoneySumByRegion generateMoneySumByRegion (
            MoneySumByRegionId moneySumByRegionId,
            RegionName regionName,
            MoneySum moneySum
    ){
        return new MoneySumByRegion(
                moneySumByRegionId.getValue(),
                regionName.getValue(),
                moneySum.getValue()
        );
    }

    @Value
    public static class MoneySumByRegionId {
        public MoneySumByRegionId(String value) {
            this.value = value;
        }
        String value ;
    }

    @Value
    public static class RegionName {
        public RegionName(String value) {
            this.value = value;
        }
        String value ;
    }

    @Value
    public static class MoneySum {
        public MoneySum(long value) {
            this.value = value;
        }
        long value ;
    }


}

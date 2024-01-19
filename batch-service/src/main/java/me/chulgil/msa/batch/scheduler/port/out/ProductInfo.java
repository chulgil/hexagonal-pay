package me.chulgil.msa.batch.scheduler.port.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfo {
    public Long id;
    public String name;
    public int price;
    public String type;
}

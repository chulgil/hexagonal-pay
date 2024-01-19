package me.chulgil.msa.batch.scheduler.port.out;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<ProductInfo> {

    @Override
    public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductInfo.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .type(rs.getString("type"))
                .build();
    }
}

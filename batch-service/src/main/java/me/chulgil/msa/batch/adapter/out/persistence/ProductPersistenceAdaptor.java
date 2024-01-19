package me.chulgil.msa.batch.adapter.out.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.scheduler.port.out.FindProductPort;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import me.chulgil.msa.batch.scheduler.port.out.ProductRowMapper;
import me.chulgil.msa.batch.scheduler.port.out.QueryParameterPort;
import me.chulgil.msa.common.PersistenceAdapter;
import org.springframework.jdbc.core.JdbcTemplate;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdaptor implements FindProductPort, QueryParameterPort {

    private final JdbcTemplate jdbcTemplate;

    public List<ProductInfo> findProductByType() {
        return jdbcTemplate.query("SELECT type FROM product group by type", new ProductRowMapper() {
            @Override
             public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ProductInfo.builder()
                        .type(rs.getString("type"))
                        .build();
            }
        });
    }

    @Override
    public Map<String, Object> getParameterForQuery(String parameter, String type) {

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("type", type);
        return parameters;
    }
}


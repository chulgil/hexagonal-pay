package me.chulgil.msa.batch.scheduler.port.out;

import java.util.Map;

public interface QueryParameterPort {

    Map<String, Object> getParameterForQuery(String parameter, String type);
}

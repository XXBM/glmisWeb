package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.attendance.AttendanceSummary;

import java.io.IOException;

/**
 * Created by ibs on 16/12/27.
 */
public class AttendanceSummaryDeserialize extends JsonDeserializer<AttendanceSummary> {
    @Override
    public AttendanceSummary deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            return null;//没获取到id就返回空
        }else {
            return new AttendanceSummary(id);
        }//返回一个对象
    }
}

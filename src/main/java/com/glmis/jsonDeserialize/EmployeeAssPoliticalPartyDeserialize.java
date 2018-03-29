package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.personnel.EmployeeAssPoliticalParty;

import java.io.IOException;

/**
 * Created by dell on 2017-04-03 .
 */
public class EmployeeAssPoliticalPartyDeserialize extends JsonDeserializer<EmployeeAssPoliticalParty> {
    @Override
    public EmployeeAssPoliticalParty deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            return null;//没获取到id就返回空
        }else {
            return new EmployeeAssPoliticalParty(id);//有id就返回一个对象
        }
    }
}

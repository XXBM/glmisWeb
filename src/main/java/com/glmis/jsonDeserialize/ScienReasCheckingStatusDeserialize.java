package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;

import java.io.IOException;

/**
 * Created by xuling on 2016/11/27.
 */
public class ScienReasCheckingStatusDeserialize extends JsonDeserializer<ScienReasCheckingStatus> {
    @Override
    public ScienReasCheckingStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,JsonProcessingException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            Long checkingId = root.findValue("id").asLong();//通过fileName找到对应的值
            return new ScienReasCheckingStatus(checkingId);
        }else {
            return new ScienReasCheckingStatus(id);
        }//返回一个对象
    }
    }

package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.booking.Adress;
import com.glmis.domain.personnel.Employee;

import java.io.IOException;

/**
 * Created by xuling on 2016/11/27.
 */
public class AdressDeserialize extends JsonDeserializer<Adress> {
    @Override
    public Adress deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Integer id = root.asInt();//得到id
        if(id==0){
            return null;//没获取到id就返回空
        }else {
            return new Adress(id);
        }//返回一个对象
    }
    }

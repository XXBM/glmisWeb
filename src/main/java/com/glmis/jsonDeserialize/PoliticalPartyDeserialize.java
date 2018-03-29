package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.personnel.PoliticalParty;

import java.io.IOException;

/**
 * Created by kene213 on 2017/3/28.
 */
public class PoliticalPartyDeserialize extends JsonDeserializer<PoliticalParty>{
    @Override
    public PoliticalParty deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            return null;//没获取到id就返回空
        }else {
            return new PoliticalParty(id);//有id就返回一个对象
        }
    }


}

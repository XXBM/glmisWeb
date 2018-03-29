package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.attendance.PresenceDescription;

import java.io.IOException;

/**
 * Created by dell on 2017-05-23 .
 */
public class PresenceDescriptionDeserialize extends JsonDeserializer {
    @Override
    public PresenceDescription deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode root = mapper.readTree(p);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            Long presenceDescriptionId = root.findValue("id").asLong();
            return new PresenceDescription(presenceDescriptionId);//没获取到id就返回空
        }else {
            return new PresenceDescription(id);
        }//返回一个对象
    }
}

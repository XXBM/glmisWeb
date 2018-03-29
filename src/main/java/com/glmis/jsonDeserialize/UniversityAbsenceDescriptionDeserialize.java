package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.attendance.UniversityAbsenceDescription;

import java.io.IOException;

/**
 * Created by dell on 2017-05-23 .
 */
public class UniversityAbsenceDescriptionDeserialize extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode root = mapper.readTree(p);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            Long textbookRankId = root.findValue("id").asLong();
            return new UniversityAbsenceDescription(textbookRankId);//没获取到id就返回空
        }else {
            return new UniversityAbsenceDescription(id);//有id就返回一个对象
        }
    }
}

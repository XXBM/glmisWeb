package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.scientificResearch.AwardLevel;

import java.io.IOException;

/**
 * Created by inkskyu428 on 17-5-10.
 */
public class AwardLevelDeserialize extends JsonDeserializer<AwardLevel> {
    @Override
    public AwardLevel deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);//读取到json
        Long id = root.asLong();//得到id
        if(id==0){
            Long awardsLevelId = root.findValue("id").asLong();
            return new AwardLevel(awardsLevelId);//没获取到id就返回空
        }else {
            return new AwardLevel(id);//有id就返回一个对象
        }
    }
}

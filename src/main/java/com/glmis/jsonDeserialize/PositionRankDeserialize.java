package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.personnel.PositionRank;

import java.io.IOException;


/**
 * Created by dell on 2016/11/19.
 * 当请求将Json内容反序列话成java类型对象的时候，该方法被调用
 * 当Json为空时，该方法不会被调用，所以，方法不需要去check该值是否为空
 */
public class PositionRankDeserialize extends JsonDeserializer<PositionRank> {
    @Override
    public PositionRank deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            JsonNode root = mapper.readTree(jp);//读取到json
            Long id = root.asLong();//得到id
            if(id==0){
                return null;//没获取到id就返回空
            }else {
            return new PositionRank(id);//有id就返回一个对象
            }
    }
}
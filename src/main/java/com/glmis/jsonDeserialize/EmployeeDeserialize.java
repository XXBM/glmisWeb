package com.glmis.jsonDeserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glmis.domain.personnel.Employee;

import java.io.IOException;


/**
 * Created by dell on 2016/11/19.
 */
public class EmployeeDeserialize extends JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            JsonNode root = mapper.readTree(jp);//读取到json
            Long id = root.asLong();//得到id
            if(id==0){
                return null;//没获取到id就返回空
            }else {
                return new Employee(id);
            }//返回一个对象
    }
}
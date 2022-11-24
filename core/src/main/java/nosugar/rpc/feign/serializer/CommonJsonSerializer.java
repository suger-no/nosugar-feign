package nosugar.rpc.feign.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nosugar.rpc.feign.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;


public class CommonJsonSerializer implements CommonSerializer{
    private static final Logger logger = LoggerFactory.getLogger(CommonJsonSerializer.class);
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param object
     * @return
     */
    @Override
    public Map<String,String> serialize(Object object) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(object);
            String s = new String(bytes);
            return JSON.parseObject(s, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("序列化时出错");
            throw new SerializeException(e.getMessage());
        }

    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj =  objectMapper.readValue(bytes,clazz);
            return obj;
        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("序列化时出错");
//            throw new SerializeException("序列化时发生错误");
            return new String(bytes);
        }

    }


    public Object deserialize(byte[] bytes, Type type) {
        String s = new String(bytes);
        Object obj = JSON.parseObject(s, new TypeReference(type) {});
        return obj;

    }
}

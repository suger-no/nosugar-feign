package nosugar.rpc.feign.serializer;

import java.lang.reflect.Type;
import java.util.Map;

public interface CommonSerializer {
    Map<String,String> serialize(Object object);

    Object deserialize(byte[] bytes,Class<?> clazz);
    @Deprecated
    public Object deserialize(byte[] bytes, Type type);
}

package org.laboys.better.spring.core.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 序列化器, 用于将枚举值转换为小写形式输出
 */
public class LowerCaseEnumValueSerializer extends StdSerializer<Enum<?>> {
    private static final long serialVersionUID = 1972770893011392159L;

    public LowerCaseEnumValueSerializer() {
        super(Enum.class, false);
    }

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }

}

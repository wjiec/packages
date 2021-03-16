package org.laboys.better.spring.core.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.Money;
import org.laboys.better.spring.core.util.MoneyUtil;

import java.io.IOException;
import java.math.BigDecimal;

public class MoneyDeserializer extends StdDeserializer<Money> {
    private static final long serialVersionUID = -8342330364001063225L;

    public MoneyDeserializer() {
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonToken token = p.getCurrentToken();
        if (token == JsonToken.VALUE_STRING) {
            return MoneyUtil.of(p.getText());
        } else if (token == JsonToken.VALUE_NUMBER_INT || token == JsonToken.VALUE_NUMBER_FLOAT) {
            return MoneyUtil.of(BigDecimal.valueOf(p.getDoubleValue()));
        }

        return (Money) ctx.handleUnexpectedToken(_valueClass, p);
    }

}

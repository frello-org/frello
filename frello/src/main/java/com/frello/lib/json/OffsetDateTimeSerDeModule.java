package com.frello.lib.json;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
        var dt = OffsetDateTime.parse(arg0.getValueAsString());
        if (dt.getOffset() != ZoneOffset.UTC) {
            throw new IOException("OffsetDateTime can only be deserialized with UTC time zone");
        }
        return dt;
    }
}

class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
    @Override
    public void serialize(OffsetDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        var timestamp = arg0.atZoneSameInstant(ZoneOffset.UTC).toString();
        arg1.writeString(timestamp);
    }
}

public class OffsetDateTimeSerDeModule extends SimpleModule {
    public OffsetDateTimeSerDeModule() {
        addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
    }
}

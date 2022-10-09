package com.frello.utils.json;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

class InstantDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
        return Instant.ofEpochMilli(arg0.getLongValue());
    }
}

class InstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        arg1.writeNumber(arg0.toEpochMilli());
    }
}

public class InstantSerdeModule extends SimpleModule {
    public InstantSerdeModule() {
        addDeserializer(Instant.class, new InstantDeserializer());
        addSerializer(Instant.class, new InstantSerializer());
    }
}

package ru.practicum;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class WeatherEventJsonDeserializer implements Deserializer<WeatherEvent> {
    private final JsonMapper jsonMapper = new JsonMapper();

    @Override
    public WeatherEvent deserialize(String topic, byte[] data) {
        try {
            return jsonMapper.readValue(data, WeatherEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

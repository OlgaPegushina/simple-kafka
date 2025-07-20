package ru.practicum;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class WeatherEventJsonDeserializer implements Deserializer<WeatherEventOld> {
    private final JsonMapper jsonMapper = new JsonMapper();

    @Override
    public WeatherEventOld deserialize(String topic, byte[] data) {
        try {
            return jsonMapper.readValue(data, WeatherEventOld.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package ru.practicum;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.serialization.Serializer;
import ru.practicum.yandex.avro.WeatherEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WeatherEventAvroSerializer implements Serializer<WeatherEvent> {
    private final EncoderFactory encoderFactory = EncoderFactory.get();

    @Override
    public byte[] serialize(String topic, WeatherEvent event) {
        if(event == null) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            BinaryEncoder encoder = encoderFactory.binaryEncoder(outputStream, null);
            DatumWriter<WeatherEvent> datumWriter = new SpecificDatumWriter<>(WeatherEvent.class);

            // сериализуем данные
            datumWriter.write(event, encoder);

            // сбрасываем все данные из буфера в поток
            encoder.flush();
            // возвращаем сериализованные данные
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new SerializationException("Ошибка сериализации экземпляра WeatherEventOld", e);
        }
    }
}

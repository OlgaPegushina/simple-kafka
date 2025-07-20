package ru.practicum;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.practicum.yandex.avro.WeatherEvent;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaAvroProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaAvroProducer.class);
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // указываем в качестве сериализатора ключа сообщения — VoidSerializer из комплекта kafka-clients
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        // указываем в качестве сериализатора данных сообщения наш Avro-сериализатор
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, WeatherEventAvroSerializer.class);

        String topic = "weather-events";

        int msgCount = 10;
        log.info("Начинаю отправку {} сообщений", msgCount);
        try (Producer<String, WeatherEvent> producer = new KafkaProducer<>(config)) {
            for (int i = 0; i < msgCount; i++) {
                WeatherEvent event = getRandomMessage();
                ProducerRecord<String, WeatherEvent> record = new ProducerRecord<>(topic, event);

                producer.send(record);
            }
        }
    }

    private static WeatherEvent getRandomMessage() {
        return WeatherEvent.newBuilder()
                .setLatitude(rnd.nextDouble(-90, 90))
                .setLongitude(rnd.nextDouble(-180, 180))
                .setTemperature(rnd.nextDouble(-100, 100))
                .build();
    }
}

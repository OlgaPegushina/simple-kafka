package ru.practicum;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaJsonConsumerExample {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.VoidDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "ru.practicum.WeatherEventJsonDeserializer");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-service-1");

        try (Consumer<Void, WeatherEventOld> consumer = new KafkaConsumer<>(config)) {
            consumer.subscribe(List.of("example-topic", "weather-events"));

            while (true) {
                ConsumerRecords<Void, WeatherEventOld> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<Void, WeatherEventOld> record : records) {
                    System.out.printf("topic = %s, offset = %d, value = %s%n", record.topic(), record.offset(), record.value());
                }
            }
        }
    }
}

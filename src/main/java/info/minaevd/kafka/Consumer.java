package info.minaevd.kafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import info.minaevd.Utils;

public class Consumer
{
    private static final int MAX_ALLOWED_LATENCY = 5000;

    private final Properties properties = getProperties();

    private final String topic;

    public Consumer( String topic )
    {
        this.topic = topic;
    }

    public Map<String, Integer> consume()
    {
        Map<String, Integer> records = new HashMap<>();

        try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties)) {
            kafkaConsumer.subscribe(Collections.singletonList(topic));

            long endPollingTimestamp = System.currentTimeMillis() + MAX_ALLOWED_LATENCY;

            while ( System.currentTimeMillis() < endPollingTimestamp ) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
                for ( ConsumerRecord<String, String> next : consumerRecords ) {
                    String[] splitted = next.value().split(":");
                    records.put(splitted[0], Integer.valueOf(splitted[1]));
                }
            }

        }

        return records;
    }

    private Properties getProperties()
    {
        Properties result = Utils.getConfigProperties();

        // add consumer specific result
        result.setProperty("enable.auto.commit", "true");
        result.setProperty("auto.commit.interval.ms", "1000");
        result.setProperty("session.timeout.ms", "30000");
        result.setProperty("metadata.max.age.ms", "1000");
        result.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        result.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return result;
    }
}

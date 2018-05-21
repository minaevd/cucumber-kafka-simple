package info.minaevd.kafka;

import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import info.minaevd.Utils;

public class Producer
{
    private final Properties properties = getProperties();

    public Producer()
    {
    }

    private Properties getProperties()
    {
        Properties properties = Utils.getConfigProperties();

        properties.put("acks", "all");
        properties.put("retries", 2);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }

    public void emit( String topicName, String message )
    {
        String partitionKey = UUID.randomUUID().toString();
        try (KafkaProducer<Object, Object> kafkaProducerLocal = new KafkaProducer<>(properties)) {
            kafkaProducerLocal.send(new ProducerRecord<>(topicName, partitionKey, message));
        }
    }
}

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerDemo {

	public static void main(String[] args) {

		final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
		final String TOPIC = "first_topic";

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, "Hello From Java Code");

		ProducerRecord<String, String> recordWithKey = new ProducerRecord<String, String>(TOPIC, "key_id_1",
				"record with Key id 1");

		// producer.send(record);

		// Producer with callback functions,
		producer.send(record, new Callback() {
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if (e != null) {
					e.printStackTrace();
				} else {
					System.out.println("The offset of the record we just sent is: " + metadata.offset());
				}
			}
		});
		
		producer.send(recordWithKey, new Callback() {
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if (e != null) {
					e.printStackTrace();
				} else {
					System.out.println("The offset of the record we just sent is: " + metadata.offset());
				}
			}
		});

		producer.close();

		producer.flush();

	}

}

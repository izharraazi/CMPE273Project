package facebook.Messaging;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


/*
 * Izhar Raazi Cmpe-273
 * 
 * SJSU-ID - 010102118 
 */

//--------------------------------------KAFKA PRODUCER -------------------------------------

public class FacebookProducerKafka {
	 private static final String TOPIC = "facebookEvent";
	 private static Producer<Integer, String> producer;
	 private final Properties properties = new Properties();

	    public FacebookProducerKafka() {
	       properties.put("metadata.broker.list", "localhost:9092");
	    	//properties.put("metadata.broker.list", "54.149.84.25:9092");
	        properties.put("serializer.class", "kafka.serializer.StringEncoder");
	        properties.put("request.required.acks", "1");
	        producer = new Producer<>(new ProducerConfig(properties));
	        
	    }
// ------------------------------------------Publish Message via Broker----------------------------------------	    
 public int  KafkaProducerPublishMessage(String p_message){
	 try{
		 ProducerConfig producerConfig = new ProducerConfig(properties);
		 kafka.javaapi.producer.Producer<String,String> producer = new kafka.javaapi.producer.Producer<String, String>(producerConfig);
		 KeyedMessage<String, String> message =new KeyedMessage<String, String>(TOPIC,p_message);
		 producer.send(message);
		 producer.close();
		 System.out
		 .println("MessageProducerKafka.KafkaProducerPublishMessage() Message Posted Successfully"+p_message+" at Time:"+System.currentTimeMillis());
		 return 0;
	 }catch(Exception e){
		 e.printStackTrace();
		 return 1;
	 }


 }

}

//https://cwiki.apache.org/confluence/display/KAFKA/KIP-98+-+Exactly+Once+Delivery+and+Transactional+Messaging#KIP98ExactlyOnceDeliveryandTransactionalMessaging-RejectedAlternatives

public class KafkaTransactionsExample {
  
  public static void main(String args[]) {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerConfig);
 
    KafkaProducer<String, String> producer = new KafkaProducer<>(producerConfig);
 
    //1.
    producer.initTransactions();
     
    while(true) {
      //2.
      ConsumerRecords<String, String> records = consumer.poll(CONSUMER_POLL_TIMEOUT);
      if (!records.isEmpty()) {
        //3.
        producer.beginTransaction();
         
        //4.
        List<ProducerRecord<String, String>> outputRecords = processRecords(records);
        for (ProducerRecord<String, String> outputRecord : outputRecords) {
          //4.
          producer.send(outputRecord);
        }
         
        //5.
        sendOffsetsResult = producer.sendOffsetsToTransaction(getUncommittedOffsets());
         
        //6.
        producer.endTransaction();
      }
    }
  }
}
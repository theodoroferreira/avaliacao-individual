package br.com.pb.msorder.framework.adapter.out.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TopicProducer {

    @Value("${topic.order-history}")
    private String topicOrderHistory;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(TopicProducer.class);

    public void sendMessage(String message) {
        kafkaTemplate.send(topicOrderHistory, message);
        LOG.info("Mensagem enviada para o topic.order-history: {}", message);
    }
}

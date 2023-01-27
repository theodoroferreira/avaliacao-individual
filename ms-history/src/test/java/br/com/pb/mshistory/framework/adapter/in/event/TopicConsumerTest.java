package br.com.pb.mshistory.framework.adapter.in.event;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.model.History;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka(controlledShutdown = true)
@DirtiesContext
class TopicConsumerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private HistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String TOPIC = "${spring.kafka.topic.order-history}";

//    @Test
//    void testConsumer() throws JsonProcessingException {
//        History history = History.builder()
//                .id("id")
//                .orderId(1L)
//                .totalValue(new BigDecimal(10))
//                .eventDateTime(LocalDateTime.of(2023, 1, 31, 17, 0))
//                .build();
//        String message = objectMapper.writeValueAsString(history);
//
//        kafkaTemplate.send(TOPIC, message);
//
//        verify(historyService).save(history);
//    }
}
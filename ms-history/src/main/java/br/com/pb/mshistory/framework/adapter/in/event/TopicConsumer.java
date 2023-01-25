package br.com.pb.mshistory.framework.adapter.in.event;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.model.History;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TopicConsumer {

    private final HistoryService service;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic.order-history}", groupId = "${spring.kafka.consumer.group-id}")
    public void getOrderHistory(String orderHistory) throws JsonProcessingException {
        log.info("Mensagem Histórico {}", orderHistory);

        History history = objectMapper.readValue(orderHistory, History.class);

        System.out.println(history.getOrder_id() + " " + history.getTotalValue());
        service.save(history);
        log.info("Order salvo na base com sucesso: {}", history);
    }
}

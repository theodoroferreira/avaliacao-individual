package br.com.pb.mshistory.application.service;

import br.com.pb.mshistory.domain.model.History;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class HistoryOrder {

    private final HistoryService service;

    @KafkaListener(topics = "${topic.history-order}", groupId = "${spring.kafka.consumer.group-id}")
    public void getOrderHistory(String historyOrder) throws JsonProcessingException {
        log.info("Mensagem Histórico {}", historyOrder);

        ObjectMapper objectMapper = new ObjectMapper();
        History history = objectMapper.readValue(historyOrder, History.class);

        service.save(history);
        log.info("Order salvo na base com sucesso: {}", history);
    }

}

package br.com.pb.msorder.framework.adapter.in.rest;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.framework.adapter.out.event.TopicProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping(value = "/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderUseCase orderService;
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderRequestDTO request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) String cpf, @RequestParam(required = false) BigDecimal totalValue, Pageable pageable) {
        return orderService.findAll(cpf, totalValue, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

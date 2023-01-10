package br.com.pb.msorder.framework.adapters.in.rest;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.OrderFilterDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) OrderFilterDTO orderFilter, Pageable pageable) {
        return this.orderService.findAll(orderFilter, pageable);
    }

}

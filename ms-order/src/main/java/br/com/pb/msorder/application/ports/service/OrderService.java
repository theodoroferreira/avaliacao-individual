package br.com.pb.msorder.application.ports.service;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.OrderFilterDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public OrderDTO create(OrderDTO request) {
        Order order = modelMapper.map(request, Order.class);
        repository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public PageableDTO findAll(OrderFilterDTO orderFilter, Pageable pageable) {
        var exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var order = Order
                .builder()
                .cpf(orderFilter.getCpf())
                .totalValue(orderFilter.getTotalValue())
                .build();

        Example<Order> example = Example.of(order, exampleMatcher);

        Page<Order> orderPage = repository.findAllExample(example, pageable);

        List<Order> orders = orderPage.getContent();

        return PageableDTO
                .builder()
                .numberOfElements(orderPage.getNumberOfElements())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .orderList(orders)
                .build();
    }
}

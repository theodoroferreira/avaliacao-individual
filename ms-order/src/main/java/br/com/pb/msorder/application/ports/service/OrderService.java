package br.com.pb.msorder.application.ports.service;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.application.ports.out.OrderRepositoryPortOut;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.OrderFilterDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapters.out.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderJpaRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public OrderDTO create(OrderDTO request) {
        Order order = modelMapper.map(request, Order.class);
        repository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public PageableDTO findAll(OrderFilterDTO orderFilter, Pageable pageable) {
        return null;
    }
}

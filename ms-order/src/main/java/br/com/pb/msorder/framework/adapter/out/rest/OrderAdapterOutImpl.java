package br.com.pb.msorder.framework.adapter.out.rest;

import br.com.pb.msorder.application.ports.out.OrderPortOut;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderAdapterOutImpl implements OrderPortOut {

    private final OrderRepository repository;

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Order> findByCpf(String cpf, Pageable pageable) {
        return repository.findByCpf(cpf, pageable);
    }

    @Override
    public Page<Order> findByTotalValue(BigDecimal totalValue, Pageable pageable) {
        return repository.findByTotalValue(totalValue, pageable);
    }

    @Override
    public Order findByItemsContains(Item item) {
        return repository.findByItemsContains(item);
    }
}

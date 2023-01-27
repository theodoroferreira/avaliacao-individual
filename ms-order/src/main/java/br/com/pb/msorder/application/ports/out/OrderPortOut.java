package br.com.pb.msorder.application.ports.out;

import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface OrderPortOut {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByCpf(String cpf, Pageable pageable);

    Page<Order> findByTotalValue(BigDecimal totalValue, Pageable pageable);

    Order findByItemsContains(Item item);
}

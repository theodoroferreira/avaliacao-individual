package br.com.pb.msorder.framework.adapters.out.repository;

import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}

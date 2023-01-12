package br.com.pb.msorder.framework.adapter.out.repository;

import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllExample(Example<Order> example, Pageable pageable);
}

package br.com.pb.msorder.framework.adapter.out.repository;

import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page findAll(Pageable pageable);

    Page findByCpf(String cpf, Pageable pageable);
}

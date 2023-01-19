package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import org.springframework.data.domain.Pageable;

public interface OrderUseCase {
    OrderDTO create(OrderRequestDTO request);

    PageableDTO findAll(String cpf, Pageable pageable);

    OrderDTO findById(Long id);

    OrderDTO update(Long id, OrderRequestDTO request);

    void delete(Long id);
}

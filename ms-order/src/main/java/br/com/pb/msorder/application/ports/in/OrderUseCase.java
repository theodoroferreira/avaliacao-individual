package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.OrderFilterDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;

public interface OrderUseCase {

    OrderDTO create(OrderDTO orderDTO);

    PageableDTO findAll(OrderFilterDTO orderFilter, Pageable pageable);
}

package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.OrderFilterDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Pageable;

public interface OrderUseCase {

    public OrderDTO create(OrderDTO orderDTO);

    public PageableDTO findAll(OrderFilterDTO orderFilter, Pageable pageable);
}

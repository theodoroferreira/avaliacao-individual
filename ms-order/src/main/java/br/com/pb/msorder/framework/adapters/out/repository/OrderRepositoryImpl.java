package br.com.pb.msorder.framework.adapters.out.repository;

import br.com.pb.msorder.application.ports.out.OrderRepositoryPortOut;
import br.com.pb.msorder.domain.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryPortOut {
}

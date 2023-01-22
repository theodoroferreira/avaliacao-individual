package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void shouldCreate_AndReturnCreated() {
    }

    @Test
    void shouldFindAll_AndReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(Order.builder().build());
        Page<Order> page = new PageImpl<>(orders);
        when(repository.findAll(pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, pageable);
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }

    @Test
    void findAll_ShouldThrowException_WhenCpfIsNull() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findByCpf("12345678900", pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(GenericException.class, () -> service.findAll("12345678900", pageable));
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

}
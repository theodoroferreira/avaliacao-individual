package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.request.AddressRequestDTO;
import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.event.TopicProducer;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.adapter.out.service.ViaCepService;
import br.com.pb.msorder.framework.exception.GenericException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @Mock
    private ViaCepService cepService;

    @Spy
    private ModelMapper modelMapper;

    private static final Long ID = 1L;

    @Test
    void shouldFindAll_AndReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(repository.findAll(pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, null, pageable);
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }

    @Test
    void findAll_InvalidCpf_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(Collections.emptyList());

        when(repository.findByCpf("38946632607", pageable)).thenReturn(page);

        assertThrows(GenericException.class, () ->
                service.findAll("38946632607", null, pageable));
    }

    @Test
    void findAll_InvalidTotalValue_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(Collections.emptyList());

        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);

        assertThrows(GenericException.class, () ->
                service.findAll(null, new BigDecimal(10), pageable));
    }

    @Test
    void whenFindAllByTotalValue_ShouldReturnPageableDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);

        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, new BigDecimal(10), pageable);

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }

    @Test
    void findAll_totalValueProvided_cpfNull_returnsCorrectPageableDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, new BigDecimal(10), pageable);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }


    @Test
    void shouldFindOrderById() {
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        OrderDTO result = service.findById(anyLong());

        assertEquals(orderDTO, result);
    }

    @Test
    void findById_InvalidId_ThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () ->
                service.findById(anyLong()));
    }

    @Test
    void update_withValidIdAndValidRequest_shouldReturnOrderDTO() {
        Order order = new Order();
        when(repository.findById(ID)).thenReturn(Optional.of(order));

        OrderRequestDTO request = OrderRequestDTO.builder().cpf(anyString()).address(new AddressRequestDTO("98700000", "243")).build();
        Address address = getAddress();
        when(cepService.findAddressByCep("98700000")).thenReturn(address);
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(new OrderDTO());

        OrderDTO result = service.update(ID, request);

        assertNotNull(result);
    }

    @Test
    void update_whenInvalidId_throwsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        OrderRequestDTO request = new OrderRequestDTO();
        assertThrows(GenericException.class, () ->
                service.update(anyLong(), request));
    }

    @Test
    void delete_ValidId_ShouldDeleteOrder() {
        Order order = new Order();
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));

        service.delete(anyLong());

        verify(repository).deleteById(anyLong());
    }

    @Test
    public void delete_InvalidId_ShouldThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () ->
                service.delete(anyLong()));
    }

    private OrderRequestDTO getOrderRequestDTO() {
        return OrderRequestDTO.builder()
                .cpf("38946632607")
                .items(Arrays.asList(getItem()))
                .address(getAddressRequestDTO())
                .build();
    }

    private AddressRequestDTO getAddressRequestDTO() {
        return AddressRequestDTO.builder()
                .cep("98700000")
                .numero("123")
                .build();
    }

    private Order getOrder() {
        return Order.builder()
                .orderId(1L)
                .build();
    }

    private Address getAddress() {
        return Address.builder()
                .cep("98700000")
                .build();
    }

    private Item getItem() {
        return Item.builder()
                .name("Item")
                .value(BigDecimal.valueOf(10))
                .description("Item description")
                .creationDate(LocalDate.of(2023, 1, 30))
                .creationDate(LocalDate.of(2023, 2, 15))
                .build();
    }

}
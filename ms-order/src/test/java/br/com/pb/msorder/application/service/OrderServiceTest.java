package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.request.AddressRequestDTO;
import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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

    @MockBean
    private CepService cepService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void shouldCreate_AndReturnCreated() {
    }

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

//    @Test
//    void whenFindAllByCpf_ShouldReturnPageableDTO() {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Order> orders = Arrays.asList(new Order());
//        Page<Order> page = new PageImpl<>(orders);
//
//        when(repository.findByCpf("38946632607", pageable)).thenReturn(page);
//
//        PageableDTO result = service.findAll("38946632607", null, pageable);
//
//        assertEquals(1, result.getNumberOfElements());
//        assertEquals(1, result.getTotalElements());
//        assertEquals(1, result.getTotalPages());
//        assertEquals(orders, result.getOrderList());
//    }

    @Test
    void findAll_InvalidCpf_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(repository.findByCpf("38946632607", pageable)).thenReturn(page);

        assertThrows(GenericException.class, () ->
            service.findAll("38946632607", null, pageable));
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

//    @Test
//    void findAll_InvalidTotalValue_ThrowsException() {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Order> orders = Arrays.asList(new Order());
//        Page<Order> page = new PageImpl<>(orders);
//
//        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);
//
//        assertThrows(GenericException.class, () ->
//                service.findAll(null, new BigDecimal(10), pageable));
//    }
//
//    @Test
//    void findAll_CpfProvided_TotalValueNull_ReturnsCorrectPageableDTO() {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Order> orders = Arrays.asList(new Order());
//        Page<Order> page = new PageImpl<>(orders);
//        when(repository.findByCpf("38946632607", pageable)).thenReturn(page);
//
//        PageableDTO result = service.findAll("38946632607", null, pageable);
//        assertEquals(1, result.getNumberOfElements());
//        assertEquals(1, result.getTotalElements());
//        assertEquals(1, result.getTotalPages());
//        assertEquals(orders, result.getOrderList());
//    }

    @Test
    void findAll_TotalValueProvided_CpfNull_ReturnsCorrectPageableDTO() {
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

//    @Test
//    void shouldUpdate_And_ReturnSuccess() {
//        Order order = new Order();
//        Address address = new Address();
//
//        OrderRequestDTO request = new OrderRequestDTO();
//
//        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
//        when(cepService.findAddressByCep(anyString())).thenReturn(address);
//        when(repository.save(any())).thenReturn(order);
//
//        OrderDTO result = service.update(anyLong(), request);
//        assertEquals("38946632607", order.getCpf());
//        assertEquals("98700000", order.getAddress().getCep());
//        assertEquals("123", order.getAddress().getNumero());
//        assertEquals(order, modelMapper.map(result, Order.class));
//    }

    @Test
    void testUpdate_invalidId_throwsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        OrderRequestDTO request = new OrderRequestDTO();
        assertThrows(GenericException.class, () ->
            service.update(anyLong(), request));
    }

//    @Test
//    public void testUpdate_nullAddressFields_updatesOrderAndReturnsCorrectOrderDTO() {
//        Long id = 1L;
//        Order order = new Order();
//        when(repository.findById(id)).thenReturn(Optional.of(order));
//
//        OrderRequestDTO request = new OrderRequestDTO();
//        request.setCpf("12345678901");
//        request.setAddress(null);
//
//        OrderDTO result = service.update(id, request);
//        assertEquals("12345678901", order.getCpf());
//        assertEquals(null, order.getAddress());
//        assertEquals(order, modelMapper.map(result, Order.class));
//    }

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
                .items(Arrays.asList(new Item()))
                .address(new AddressRequestDTO())
                .build();
    }

    private AddressRequestDTO getAddressRequestDTO() {
        return AddressRequestDTO.builder()
                .cep("98700000")
                .numero("123")
                .build();
    }

}
package br.com.pb.msorder.framework.adapter.in.rest;

import br.com.pb.msorder.application.service.OrderService;
import br.com.pb.msorder.domain.dto.request.AddressRequestDTO;
import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.framework.adapter.out.service.ViaCepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private static final Long ID = 1L;

    private static final String URL = "/orders";

    private static final String ID_URL = "/orders/" + ID;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ViaCepService cepService;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        OrderDTO orderDTO = getOrderDTO();
        when(orderService.create(any(OrderRequestDTO.class))).thenReturn(orderDTO);
        Address address = new Address();
        when(cepService.findAddressByCep(anyString())).thenReturn(address);

        OrderRequestDTO request = getOrderRequestDTO();
        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void findAll() throws Exception {
        PageableDTO pageableDTO = new PageableDTO();
        when(orderService.findAll(any(), any(), any())).thenReturn(pageableDTO);
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void findById() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.findById(any())).thenReturn(orderDTO);
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void update() throws Exception {
        OrderDTO orderDTO = getOrderDTO();
        when(orderService.update(anyLong(), any(OrderRequestDTO.class))).thenReturn(orderDTO);
        Address address = new Address();
        when(cepService.findAddressByCep(anyString())).thenReturn(address);

        OrderRequestDTO request = getOrderRequestDTO();
        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(orderService).delete(ID);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    private OrderDTO getOrderDTO() {
        return OrderDTO.builder()
                .cpf("38946632607")
                .items(Arrays.asList(new Item()))
                .totalValue(BigDecimal.TEN)
                .address(new Address())
                .build();
    }

    private OrderRequestDTO getOrderRequestDTO() {
        return OrderRequestDTO.builder()
                .cpf("38946632607")
                .items(Arrays.asList(new Item()))
                .address(new AddressRequestDTO())
                .build();
    }
}
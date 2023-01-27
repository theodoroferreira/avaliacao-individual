package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.response.ItemDTO;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.ItemRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository repository;

    @Mock
    private OrderRepository orderRepository;

    @Spy
    private ModelMapper modelMapper;

    private static final Long ID = 1L;

    private Item item;
    private ItemDTO itemDTO;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(ID);
        item.setCreationDate(LocalDate.now());
        item.setExpirationDate(LocalDate.now().plusDays(1));
        item.setValue(BigDecimal.valueOf(10));
        item.setDescription("Item Test");

        itemDTO = new ItemDTO();
        itemDTO.setCreationDate(LocalDate.now());
        itemDTO.setExpirationDate(LocalDate.now().plusDays(1));
        itemDTO.setValue(BigDecimal.valueOf(10));
        itemDTO.setDescription("Item Test");

    }

    @Test
    void patch_ShouldUpdateItem_WhenItemExists() {
        when(repository.findById(ID)).thenReturn(Optional.of(item));
        when(repository.save(item)).thenReturn(item);

        Order order = new Order();
        order.setItems(List.of(item));

        when(orderRepository.findByItemsContains(item)).thenReturn(order);

        ItemDTO result = itemService.patch(ID, itemDTO);

        assertEquals(result, itemDTO);
    }

    @Test
    void patch_ShouldThrowException_WhenItemDoesNotExist() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () -> itemService.patch(ID, itemDTO));
    }

//    @Test
//    void patch_ShouldThrowException_WhenExpirationDateIsBeforeCreationDate() {
//        when(repository.findById(ID)).thenReturn(Optional.of(item));
//
//        itemDTO.setExpirationDate(LocalDate.now().minusDays(2));
//
//        assertThrows(GenericException.class, () -> itemService.patch(ID, itemDTO));
//    }

    @Test
    void whenPatchMethodIsCalled_thenUpdateOrderTotalValue() {
        when(repository.findById(ID)).thenReturn(Optional.of(item));

        Order order = new Order();
        List<Item> items = new ArrayList<>();
        items.add(item);
        order.setItems(items);
        when(orderRepository.findByItemsContains(item)).thenReturn(order);

        ItemDTO request = new ItemDTO();
        request.setValue(BigDecimal.valueOf(20));

        ItemDTO result = itemService.patch(ID, request);

        assertEquals(BigDecimal.valueOf(20), result.getValue());
        verify(orderRepository, times(1)).save(order);
    }
}
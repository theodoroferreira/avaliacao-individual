package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.response.ItemDTO;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.framework.adapter.out.repository.ItemRepository;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService service;

    @Mock
    private ItemRepository repository;

    @Spy
    private ModelMapper modelMapper;

//    @Test
//    public void patch_whenIdExists_shouldUpdateItem() {
//
//        Item item = Item.builder().id(1L).value(BigDecimal.TEN).description("item 1").build();
//        when(repository.findById(item.getId())).thenReturn(Optional.of(item));
//
//        ItemDTO request = ItemDTO.builder().value(BigDecimal.ONE).description("item updated").build();
//        ItemDTO updatedItem = service.patch(item.getId(), request);
//
//        assertEquals(request.getValue(), updatedItem.getValue());
//        assertEquals(request.getDescription(), updatedItem.getDescription());
//    }
}
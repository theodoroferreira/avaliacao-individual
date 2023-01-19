package br.com.pb.msorder.application.service;

import br.com.pb.msorder.application.ports.in.ItemUseCase;
import br.com.pb.msorder.domain.dto.response.ItemDTO;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.framework.adapter.out.repository.ItemRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService implements ItemUseCase {

    private final ItemRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public ItemDTO patch(Long id, Map<String, Object> fields) {
        Item item = repository
            .findById(id)
            .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));

        fields.forEach(
            (key, value) -> {
                Field field = ReflectionUtils.findField(Item.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, item, value);
            }
        );

        repository.save(item);
        return modelMapper.map(item, ItemDTO.class);
    }
}

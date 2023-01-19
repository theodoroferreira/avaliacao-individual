package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.response.ItemDTO;

import java.util.Map;

public interface ItemUseCase {
    ItemDTO patch(Long id, Map<String, Object> fields);
}

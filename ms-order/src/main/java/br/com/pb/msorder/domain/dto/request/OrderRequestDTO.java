package br.com.pb.msorder.domain.dto.request;

import br.com.pb.msorder.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private String cpf;
    private List<Item> items;
    private AddressRequestDTO address;
}

package br.com.pb.msorder.domain.dto.response;

import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @Min(value = 11, message = "Campo CPF deve conter apenas dígitos.")
    private String cpf;
    private List<Item> items;
    private BigDecimal totalValue;
    private Address address;
}

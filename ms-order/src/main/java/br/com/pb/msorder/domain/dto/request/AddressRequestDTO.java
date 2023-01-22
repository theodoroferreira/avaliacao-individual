package br.com.pb.msorder.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    @NotBlank(message = "Campo de CEP deve conter apenas números.")
    @Min(value = 8, message = "Campo de CEP deve conter extamente 8 digitos.")
    @Max(value = 8)
    private String cep;
}

package br.com.pb.msorder.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProducerDTO {

    private Long order_id;
    private BigDecimal totalValue;
}

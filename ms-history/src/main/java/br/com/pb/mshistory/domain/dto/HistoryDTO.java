package br.com.pb.mshistory.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    private Long order_id;
    private BigDecimal totalValue;
}

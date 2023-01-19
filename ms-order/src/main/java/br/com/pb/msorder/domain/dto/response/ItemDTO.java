package br.com.pb.msorder.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private String name;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private BigDecimal value;
    private String description;
}

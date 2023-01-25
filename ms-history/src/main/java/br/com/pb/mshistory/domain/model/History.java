package br.com.pb.mshistory.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    private String id;
    private Long order_id;
    private BigDecimal totalValue;
}

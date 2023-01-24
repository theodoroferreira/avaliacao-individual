package br.com.pb.mshistory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    private UUID uuid;
    private Long order_id;
    private BigDecimal totalValue;
}

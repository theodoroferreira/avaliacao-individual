package br.com.pb.msorder.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "validation_date")
    private LocalDateTime validationDate;
    private BigDecimal value;
    private String description;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
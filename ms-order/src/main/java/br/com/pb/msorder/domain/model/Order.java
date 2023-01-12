package br.com.pb.msorder.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CPF
    @Column(nullable = false)
    private String cpf;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;
    @Column(nullable = false)
    private BigDecimal totalValue;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
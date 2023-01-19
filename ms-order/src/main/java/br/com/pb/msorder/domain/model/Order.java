package br.com.pb.msorder.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPF(message = "CPF inválido")
    private String cpf;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    private BigDecimal totalValue;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}

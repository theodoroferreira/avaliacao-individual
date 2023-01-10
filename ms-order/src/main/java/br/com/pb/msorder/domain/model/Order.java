package br.com.pb.msorder.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CPF
    @Column(nullable = false)
    private String cpf;
    @OneToMany(mappedBy = "order")
    private List<Item> items;
    @Column(nullable = false)
    private BigDecimal totalValue;
    @OneToOne
    private Address address;
}
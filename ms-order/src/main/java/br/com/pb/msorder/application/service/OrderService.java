package br.com.pb.msorder.application.service;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.request.OrderRequestDTO;
import br.com.pb.msorder.domain.dto.response.OrderDTO;
import br.com.pb.msorder.domain.dto.response.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository repository;

    private final ModelMapper modelMapper;

    private final CepService cepService;

    public OrderDTO create(OrderRequestDTO request) {
        Order order = modelMapper.map(request, Order.class);
        this.validateDates(order);
        order.setAddress(getAddress(request.getAddress().getCep()));
        this.validateCep(order);
        order.setTotalValue(calculateTotalValue(order));
        repository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public PageableDTO findAll(String cpf, BigDecimal totalValue, Pageable pageable) {
        Page<Order> page;
        if (cpf == null || cpf.trim().length() == 0) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findByCpf((cpf.trim()), pageable);
            if (page.isEmpty()) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "Nenhum pedido encontrado com esse CPF.");
            }
        }
        if (totalValue == null) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findByTotalValue(totalValue, pageable);
            if (page.isEmpty()) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "Nenhum pedido encontrado com esse CPF.");
            }
        }

        List<Order> orders = page.getContent();

        return PageableDTO
            .builder()
            .numberOfElements(page.getNumberOfElements())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .orderList(orders)
            .build();
    }

    @Override
    public OrderDTO findById(Long id) {
        Order order = repository
            .findById(id)
            .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO update(Long id, OrderRequestDTO request) {
        Order order = repository
            .findById(id)
            .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));

        order.setCpf(request.getCpf());
        order.setAddress(getAddress(request.getAddress().getCep()));

        repository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));
        repository.deleteById(id);
    }

    private BigDecimal calculateTotalValue(Order order) {
        List<Item> items = order.getItems();
        BigDecimal totalValue = new BigDecimal(0);
        for (Item item : items) {
            totalValue = totalValue.add(item.getValue());
        }
        return totalValue;
    }

    private Address getAddress(String cep) {
        Address request = cepService.findAddressByCep(cep);
        Address address = new Address();
        address.setCep(request.getCep());
        address.setLogradouro(request.getLogradouro());
        address.setComplemento(request.getComplemento());
        address.setBairro(request.getBairro());
        address.setLocalidade(request.getLocalidade());
        address.setUf(request.getUf());
        return address;
    }

    private void validateDates(Order order) {
        List<Item> items = order.getItems();
        for (Item item : items) {
            if (item.getExpirationDate().isBefore(item.getCreationDate())) {
                throw new GenericException(
                    HttpStatus.BAD_REQUEST,
                    "A data de expiração não pode ser anterior à data de criação."
                );
            }
        }
    }

    private void validateCep(Order order) {
        if (order.getAddress().getUf()==null) {
            throw new GenericException(
                    HttpStatus.BAD_REQUEST,
                    "CEP inválido"
            );
        }
    }

}

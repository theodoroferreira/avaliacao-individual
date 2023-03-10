package br.com.pb.msorder.framework.adapter.out.rest;

import br.com.pb.msorder.application.ports.out.ItemPortOut;
import br.com.pb.msorder.framework.adapter.out.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemAdapterOutImpl implements ItemPortOut {

    private final ItemRepository repository;

}

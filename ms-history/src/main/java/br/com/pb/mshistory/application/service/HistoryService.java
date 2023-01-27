package br.com.pb.mshistory.application.service;

import br.com.pb.mshistory.application.ports.in.HistoryUseCase;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import br.com.pb.mshistory.domain.model.History;
import br.com.pb.mshistory.framework.adapter.out.repository.HistoryRepository;
import br.com.pb.mshistory.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryUseCase {

    private final HistoryRepository repository;

    @Override
    public History save(History request) {
        request.setEventDate(LocalDate.now());
        return repository.save(request);
    }

    @Override
    public PageableDTO findAll(LocalDate eventDate, Pageable pageable) {
        Page<History> page;
        if (eventDate == null) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findByEventDate(eventDate, pageable);
            if (page.isEmpty()) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "Nenhum pedido encontrado com essa data.");
            }
        }

        List<History> history = page.getContent();

        return PageableDTO
                .builder()
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .historyList(history)
                .build();
    }
}

package br.com.pb.mshistory.application.service;

import br.com.pb.mshistory.domain.dto.HistoryDTO;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import br.com.pb.mshistory.domain.model.History;
import br.com.pb.mshistory.framework.adapter.out.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository repository;

    public History save(History history) {
        return repository.save(history);
    }

    public PageableDTO findAll(History request, Pageable pageable) {
        Page<History> page;

        page = repository.findAll(pageable);

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

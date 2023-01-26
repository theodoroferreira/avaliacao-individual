package br.com.pb.mshistory.application.ports.in;

import br.com.pb.mshistory.domain.dto.PageableDTO;
import br.com.pb.mshistory.domain.model.History;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface HistoryUseCase {

    History save(History history);

    PageableDTO findAll(LocalDateTime eventDateTime, Pageable pageable);
}

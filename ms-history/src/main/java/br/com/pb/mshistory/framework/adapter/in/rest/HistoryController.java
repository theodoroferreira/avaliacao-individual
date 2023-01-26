package br.com.pb.mshistory.framework.adapter.in.rest;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import br.com.pb.mshistory.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService service;

    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")LocalDateTime eventDateTime, Pageable pageable) {
        return service.findAll(eventDateTime, pageable);
    }
}

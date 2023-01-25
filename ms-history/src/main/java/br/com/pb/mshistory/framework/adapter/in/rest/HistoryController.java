package br.com.pb.mshistory.framework.adapter.in.rest;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.dto.HistoryDTO;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService service;

    @GetMapping
    public PageableDTO getAll(@RequestParam(required = false) HistoryDTO request, Pageable pageable) {
        return service.findAll(request, pageable);
    }
}

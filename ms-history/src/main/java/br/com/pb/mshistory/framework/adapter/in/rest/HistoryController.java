package br.com.pb.mshistory.framework.adapter.in.rest;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService service;

    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate eventDate, @SortDefault(sort = "eventDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(eventDate, pageable);
    }
}

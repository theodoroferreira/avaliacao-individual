package br.com.pb.msorder.framework.adapter.in.rest;

import br.com.pb.msorder.application.ports.in.ItemUseCase;
import br.com.pb.msorder.domain.dto.response.ItemDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/items")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemUseCase itemService;

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDTO> patch(@PathVariable Long id, @RequestBody @Valid ItemDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.patch(id, request));
    }
}

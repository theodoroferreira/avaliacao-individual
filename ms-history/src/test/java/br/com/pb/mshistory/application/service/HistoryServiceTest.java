package br.com.pb.mshistory.application.service;

import br.com.pb.mshistory.domain.dto.PageableDTO;
import br.com.pb.mshistory.domain.model.History;
import br.com.pb.mshistory.framework.adapter.out.repository.HistoryRepository;
import br.com.pb.mshistory.framework.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @InjectMocks
    private HistoryService service;

    @Mock
    private HistoryRepository repository;

    @Test
    void shouldCreatePayment_whenValidPaymentDTO() {
        History request = History.builder()
                .eventDate(LocalDate.of(2023, 1, 31))
                .orderId(1L)
                .totalValue(BigDecimal.valueOf(10))
                .build();

        LocalDate now = LocalDate.now();
        History expected = History.builder()
                .eventDate(now)
                .orderId(1L)
                .totalValue(BigDecimal.valueOf(10))
                .build();

        when(repository.save(request)).thenReturn(expected);

        History result = service.save(request);

        assertEquals(expected, result);
    }

    @Test
    void shouldfindAll_whenEventDateTime_isNull() {
        Pageable pageable = mock(Pageable.class);

        Page<History> page = mock(Page.class);
        when(page.getContent()).thenReturn(Arrays.asList(new History()));
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(repository.findAll(pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, pageable);

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getHistoryList().size());
    }

    @Test
    void shouldFindAll_whenEventDateTime_isNotNull() {
        LocalDate eventDate = LocalDate.of(2023, 1, 31);
        Pageable pageable = mock(Pageable.class);

        Page<History> page = mock(Page.class);
        when(page.getContent()).thenReturn(Arrays.asList(new History()));
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(repository.findByEventDate(eventDate, pageable)).thenReturn(page);

        PageableDTO result = service.findAll(eventDate, pageable);

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getHistoryList().size());
    }

    @Test
    void shouldThrow_genericException_whenEventDateTimeDoesNotExist() {
        LocalDate eventDate = LocalDate.of(2023, 1, 31);
        Pageable pageable = PageRequest.of(0, 10);
        Page<History> emptyPage = Page.empty();

        when(repository.findByEventDate(eventDate, pageable)).thenReturn(emptyPage);

        assertThrows(GenericException.class, () -> service.findAll(eventDate, pageable));
    }
}
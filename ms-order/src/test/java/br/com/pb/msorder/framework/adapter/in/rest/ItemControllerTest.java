package br.com.pb.msorder.framework.adapter.in.rest;

import br.com.pb.msorder.application.service.ItemService;
import br.com.pb.msorder.domain.dto.response.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private static final Long ID = 1L;

    private static final String ID_URL = "/items/" + ID;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    void patch() throws Exception{
        ItemDTO itemDTO = ItemDTO.builder().name("item name").build();
        when(itemService.patch(ID, itemDTO)).thenReturn(itemDTO);

        String json = objectMapper.writeValueAsString(itemDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .patch(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
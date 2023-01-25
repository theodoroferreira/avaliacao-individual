package br.com.pb.msorder.framework.exception;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderExceptionHandlerTest {

    @Test
    void handleExceptionInternal() {
        Exception ex = new Exception("test message");
        WebRequest request = mock(WebRequest.class);

        OrderExceptionHandler handler = new OrderExceptionHandler();
        ResponseEntity<Object> response = handler.handleExceptionInternal(
                ex,
                null,
                null,
                HttpStatus.BAD_REQUEST,
                request
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        List<String> message = Collections.singletonList("test message");
        assertEquals(message, errorResponse.getMessage());
    }


    @Test
    void handle() {
        Exception ex1 = mock(Exception.class);
        GenericException ex2 = mock(GenericException.class);
        when(ex2.getMessageDTO()).thenReturn("test message");
        when(ex2.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

        OrderExceptionHandler handler = new OrderExceptionHandler();
        ResponseEntity<Object> response = handler.handle(ex1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        List<String> message1 = Collections.singletonList(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        assertEquals(message1, errorResponse.getMessage());

        response = handler.handle(ex2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        errorResponse = (ErrorResponse) response.getBody();
        List<String> message2 = Collections.singletonList("test message");
        assertEquals(message2, errorResponse.getMessage());
    }

    @Test
    void handleDefault() {
        OrderExceptionHandler handler = new OrderExceptionHandler();
        ResponseEntity<Object> response = handler.handleDefault();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        List<String> message = Collections.singletonList(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    void handleGenericException() {
        GenericException ex = mock(GenericException.class);
        when(ex.getMessageDTO()).thenReturn("test message");
        when(ex.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

        OrderExceptionHandler handler = new OrderExceptionHandler();
        ResponseEntity<Object> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        List<String> message = Collections.singletonList("test message");
        assertEquals(message, errorResponse.getMessage());
    }

//    @Test
//    void handleMethodArgumentNotValid_withInvalidArgument_returnsBadRequest() {
//        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
//        BindingResult bindingResult = mock(BindingResult.class);
//        FieldError fieldError = mock(FieldError.class);
//        when(exception.getBindingResult()).thenReturn(bindingResult);
//        when(bindingResult.getFieldError()).thenReturn(fieldError);
//        when(fieldError.getDefaultMessage()).thenReturn("Invalid argument");
//        HttpHeaders headers = mock(HttpHeaders.class);
//        HttpStatusCode status = HttpStatus.BAD_REQUEST;
//        WebRequest request = mock(WebRequest.class);
//
//        // When
//        ResponseEntity<Object> response =
//                handleMethodArgumentNotValid(exception, headers, status, request);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
//        assertEquals("Invalid argument", errorResponse.getMessage().get(0));
//    }
}
package dz.opt.feteKorner.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<ApiExceptionDTO> handleArgumentException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).toList() ;
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message("Les données saisies ne sont pas correctes").
                httpStatus(HttpStatus.BAD_REQUEST).localDateTime(LocalDateTime.now()).
                errors(errors).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.BAD_REQUEST);
    }

    // Gestion des exceptions inattendue
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiExceptionDTO> handleUnknownException(Exception e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message("Exception inconnue").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .localDateTime(LocalDateTime.now()).build();
        return  new ResponseEntity<>(apiExceptionDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

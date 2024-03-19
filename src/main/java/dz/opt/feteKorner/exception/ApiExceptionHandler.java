package dz.opt.feteKorner.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionDTO> userNotFound(BadCredentialsException e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message("Email ou Mot de passe incorrect!").
                httpStatus(HttpStatus.UNAUTHORIZED).localDateTime(LocalDateTime.now()).
                errors(List.of()).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ApiExceptionDTO> userExist(UserExistException e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message(e.getMessage()).
                httpStatus(HttpStatus.CONFLICT).localDateTime(LocalDateTime.now()).
                errors(List.of()).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionDTO> userNotFound(UsernameNotFoundException e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message(e.getMessage()).
                httpStatus(HttpStatus.UNAUTHORIZED).localDateTime(LocalDateTime.now()).
                errors(List.of()).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ExpiredVerificationLinkException.class)
    public ResponseEntity<ApiExceptionDTO> expiredLink(ExpiredVerificationLinkException e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message(e.getMessage()).
                httpStatus(HttpStatus.UNAUTHORIZED).localDateTime(LocalDateTime.now()).
                errors(List.of()).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccountNotValidYetException.class)
    public ResponseEntity<ApiExceptionDTO> accountNotValidated(AccountNotValidYetException e){
        log.error(e.getMessage());
        ApiExceptionDTO apiExceptionDTO = ApiExceptionDTO.builder().message(e.getMessage()).
                httpStatus(HttpStatus.UNAUTHORIZED).localDateTime(LocalDateTime.now()).
                errors(List.of()).
                build();

        return new ResponseEntity<>(  apiExceptionDTO,HttpStatus.UNAUTHORIZED);
    }









}

package dz.opt.feteKorner.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@RequiredArgsConstructor
@Data
public class ApiExceptionDTO {

    private  final String message;

    private  final List<String> errors;


    private final HttpStatus httpStatus;

    private  final LocalDateTime localDateTime;

}
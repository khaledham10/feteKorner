package dz.opt.feteKorner.dto;

import dz.opt.feteKorner.cste.DataInputError;
import dz.opt.feteKorner.cste.RegEx;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Ref;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {
    @NotBlank(message = DataInputError.PSEUDO_NOT_BLANK_MESSAGE)
    private String pseudo;

    @NotBlank(message = DataInputError.EMAIL_NOT_BLANK_MESSAGE)
    @Pattern(regexp = RegEx.EMAIL_PATTERN_REGEX, message = DataInputError.EMAIL_PATTERN_MESSAGE)
    private String email;

    @NotBlank(message = DataInputError.PASSWORD_NOT_BLANK_MESSAGE)
    @Pattern(regexp = RegEx.PASSWORD_PATTERN_REGEX,message = DataInputError.PASSWORD_PATTERN_MESSAGE)
    private String password;

    @NotBlank(message = DataInputError.PHONE_NOT_BLANK_MESSAGE)
    @Pattern(regexp=RegEx.PHONE_PATTERN_REGEX, message=DataInputError.PHONE_PATTERN_MESSAGE)
    @Size( min = 10,max = 10, message = DataInputError.PHONE_SIZE_MESSAGE)
    private String phone;}

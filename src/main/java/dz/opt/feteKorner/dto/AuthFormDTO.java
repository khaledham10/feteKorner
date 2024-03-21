package dz.opt.feteKorner.dto;

import dz.opt.feteKorner.cste.DataInputError;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthFormDTO {
    @NotBlank(message = DataInputError.EMAIL_NOT_BLANK_MESSAGE)
    private String email;
    @NotBlank(message = DataInputError.PASSWORD_NOT_BLANK_MESSAGE)
    private String password;

}

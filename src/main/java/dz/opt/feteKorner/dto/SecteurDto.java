package dz.opt.feteKorner.dto;
import dz.opt.feteKorner.cste.DataInputError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SecteurDto {

    private int id;
    @NotBlank(message = DataInputError.LABEL_NOT_BLANK_MESSAGE)
    private String label;
    @NotBlank(message = DataInputError.EMOJI_NOT_BLANK_MESSAGE)
    private String emoji;
}

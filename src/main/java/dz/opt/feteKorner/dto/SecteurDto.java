package dz.opt.feteKorner.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SecteurDto {

    private int id;
    @NotBlank(message = "Le libelle est obligatoire")
    private String label;
}

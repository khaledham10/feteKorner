package dz.opt.feteKorner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "Le pseudo ne peut pas être vide")
    private String pseudo;

    @NotBlank(message = "L'email ne peut pas être vide")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email incorrect")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Pattern(regexp = "^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d).{8,}$",message = "Le mot de passe doit contenir au moins 8 caractères, inclure " +
            "au moins un chiffre et une lettre majuscule")
    private String password;

    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    @Pattern(regexp="^[0-9]+$", message="Le numéro de téléphone ne peut contenir que des chiffres")
    @Size( min = 10,max = 10, message = "Le numéro doit contenir 10 caractères")
    private String phone;}

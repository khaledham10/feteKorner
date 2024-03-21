package dz.opt.feteKorner.controller.api;

import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.dto.SignUpDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthController {


    @PostMapping("/signin")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthFormDTO authFormDTO);

    @PostMapping("/signup")
    ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO);

}



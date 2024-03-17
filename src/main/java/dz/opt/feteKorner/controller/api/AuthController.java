package dz.opt.feteKorner.controller.api;

import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {


    @PostMapping("/signin")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthFormDTO authFormDTO);
}

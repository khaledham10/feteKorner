package dz.opt.feteKorner.controller.Impl;

import dz.opt.feteKorner.controller.api.AuthController;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.dto.SignUpDTO;
import dz.opt.feteKorner.service.intrf.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<JwtResponse> login(AuthFormDTO authFormDTO) {
        return new ResponseEntity(this.authService.signIn(authFormDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity signUp(SignUpDTO signUpDTO) {
        this.authService.signUp(signUpDTO);
        return new ResponseEntity(HttpStatus.CREATED);

    }


}

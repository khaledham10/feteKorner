package dz.opt.feteKorner.service.intrf;

import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.dto.SignUpDTO;

public interface AuthService {

    JwtResponse signIn(AuthFormDTO authFormDTO);

    void SignUp(SignUpDTO signUpDTO);

    void verification(String code);
}

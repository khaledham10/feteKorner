package dz.opt.feteKorner.service.intrf;

import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;

public interface AuthService {

    JwtResponse signIn(AuthFormDTO authFormDTO);
}

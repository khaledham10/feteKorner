package dz.opt.feteKorner.service.impl;

import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.service.intrf.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signIn(AuthFormDTO authFormDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authFormDTO.getEmail(), authFormDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.buildToken(authentication);
        return new JwtResponse(jwt,authFormDTO.getEmail());
    }
}

package dz.opt.feteKorner.service.impl;

import dz.opt.feteKorner.cste.AuthErrorCste;
import dz.opt.feteKorner.model.User;
import dz.opt.feteKorner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  this.userRepository.findById(email).orElseThrow(()->
              new UsernameNotFoundException(AuthErrorCste.UNKNONW_USER + email)
        );
    }
}

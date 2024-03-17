package dz.opt.feteKorner.service.impl;

import dz.opt.feteKorner.model.User;
import dz.opt.feteKorner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =this.userRepository.findByEmail(email).get(0);
        if(user==null){
            log.error("Utilisateur inconnu :"+email);
            throw new UsernameNotFoundException("Utilisateur inconnu");
        }

        return user;
    }
}

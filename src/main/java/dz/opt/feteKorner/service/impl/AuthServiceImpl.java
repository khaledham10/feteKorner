package dz.opt.feteKorner.service.impl;

import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.dto.SignUpDTO;
import dz.opt.feteKorner.exception.AccountNotValidYetException;
import dz.opt.feteKorner.exception.ExpiredVerificationLinkException;
import dz.opt.feteKorner.exception.InvalidTokenException;
import dz.opt.feteKorner.exception.UserExistException;
import dz.opt.feteKorner.model.User;
import dz.opt.feteKorner.repository.UserRepository;
import dz.opt.feteKorner.service.intrf.AuthService;
import dz.opt.feteKorner.util.Role;
import dz.opt.feteKorner.util.api.ProcessMail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProcessMail processMail;
    private  final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse signIn(AuthFormDTO authFormDTO) {
        User user = userRepository.findById(authFormDTO.getEmail()).
                orElseThrow(()->new UsernameNotFoundException("Utilisateur inconnu : "+authFormDTO.getEmail()));
        if(!user.isAccountValidated()){
            throw  new AccountNotValidYetException("Vous n'avez pas encore validé votre compte (Lien reçu par email)");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authFormDTO.getEmail(), authFormDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.buildToken(authentication);
        return new JwtResponse(jwt,authFormDTO.getEmail());
    }

    @Override
    public void SignUp(SignUpDTO signUpDTO) {
        userRepository.findById(signUpDTO.getEmail()).ifPresent((user)->{
             throw new UserExistException("Adresse email déja utilisée");
        });
        String randomCode = RandomString.make(64);
        User newUser = User.builder()
                .pseudo(signUpDTO.getPseudo())
                .email(signUpDTO.getEmail())
                .phone(signUpDTO.getPhone())
                .role(Role.CLIENT)
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .verificationCode(randomCode).build();
        this.userRepository.save(newUser);
        this.processMail.SendVerificationMail(signUpDTO.getEmail(),randomCode);

    }

    @Override
    public void verification(String code) {
        User user= this.userRepository.findByVerificationCode(code).get(0);
        if(user==null){
            throw new ExpiredVerificationLinkException("Expiration du lien, veuillez reformulez votre inscription");
        }

        user.setAccountValidated(true);
        userRepository.save(user);

    }
}

package dz.opt.feteKorner.service;

import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.cste.AuthErrorCste;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.exception.AccountNotValidYetException;
import dz.opt.feteKorner.model.User;
import dz.opt.feteKorner.repository.UserRepository;
import dz.opt.feteKorner.service.impl.AuthServiceImpl;
import dz.opt.feteKorner.service.intrf.AuthService;
import dz.opt.feteKorner.util.api.ProcessMail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private  JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ProcessMail processMail;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;



    private AuthFormDTO authFormDTO;

    private Authentication authentication;

    private User user;


    @BeforeEach
    public void init(){
        this.user=User.builder().pseudo("test").email("hello@test.fr").phone("0664578965").build();
        this.authentication = new UsernamePasswordAuthenticationToken(user,"CLIENT");
        this.authFormDTO=AuthFormDTO.builder().email("hello@test.fr").password("testPwd17!").build();



    }

    @Test
    public void AuthService_SignIn_Success(){
        user.setAccountValidated(true);
        when(userRepository.findById(authFormDTO.getEmail())).thenReturn(Optional.ofNullable(user));
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authFormDTO.getEmail(),authFormDTO.getPassword()))).thenReturn(authentication);
        when(jwtService.buildToken(authentication)).thenReturn("access_token");
        JwtResponse jwtResponse= authService.signIn(authFormDTO);
        assertTrue("access_token"== jwtResponse.getAccess_token());
        verify(userRepository, times(1)).findById(authFormDTO.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).buildToken(authentication);


    }

    @Test
    public void AuthService_SignIn_User_Not_Found(){
        when(userRepository.findById(authFormDTO.getEmail())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(UsernameNotFoundException.class,()-> {
            authService.signIn(authFormDTO);
        });

        assertEquals(exception.getMessage(), AuthErrorCste.UNKNONW_USER+authFormDTO.getEmail());
    }

    @Test
    public void AuthService_SignIn_Bad_Credential(){
        when(userRepository.findById(authFormDTO.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(AuthErrorCste.BAD_CREDENTIAL));
        Exception exception = assertThrows(BadCredentialsException.class,()-> {
            authService.signIn(authFormDTO);
        });

        assertEquals(exception.getMessage(), AuthErrorCste.BAD_CREDENTIAL);
    }

    @Test
    public void AuthService_SignIn_Account_Not_Valid(){
        user.setAccountValidated(false);
        when(userRepository.findById(authFormDTO.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authFormDTO.getEmail(),authFormDTO.getPassword()))).thenReturn(authentication);
        Exception exception = assertThrows(AccountNotValidYetException.class,()-> {
            authService.signIn(authFormDTO);
        });

        assertEquals(exception.getMessage(), AuthErrorCste.ACCOUNT_NOT_YET_VALIDE);
    }


}

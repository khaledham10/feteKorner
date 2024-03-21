package dz.opt.feteKorner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.controller.Impl.AuthControllerImpl;
import dz.opt.feteKorner.controller.api.AuthController;
import dz.opt.feteKorner.cste.AuthErrorCste;
import dz.opt.feteKorner.cste.DataInputError;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.JwtResponse;
import dz.opt.feteKorner.dto.SignUpDTO;
import dz.opt.feteKorner.exception.AccountNotValidYetException;
import dz.opt.feteKorner.exception.ExpiredVerificationLinkException;
import dz.opt.feteKorner.exception.UserExistException;
import dz.opt.feteKorner.service.intrf.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest  {
    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private SignUpDTO signUpDTO;

    private AuthFormDTO authFormDTO;

    private  ObjectMapper objectMapper;

    private JwtResponse jwtResponse;

    @MockBean
    private JwtService jwtService;




    @BeforeEach
    public void init(){
        this.objectMapper=new ObjectMapper();
        this.signUpDTO= SignUpDTO.builder().email("test@test.fr")
                .password("testpwdL1985")
                .pseudo("hello")
                .phone("0778899874")
                .build();
        this.authFormDTO = AuthFormDTO.builder().email("test@gmail.com").password("Test@457L").build();
        jwtResponse=JwtResponse.builder().access_token("jejejkke5d5fd8f8sdokfosdkfdokfo").email(authFormDTO.getEmail()).build();


    }


    @Test
    public void AuthController_SignUp_Success() throws Exception {
       when(authService.signUp(signUpDTO)).thenReturn(signUpDTO.getEmail());
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDTO))).andExpect(status().isCreated());

    }

    @Test
    public void AuthController_SignUp_Bad_Email() throws Exception {
        signUpDTO.setEmail("bad_email");
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(DataInputError.BAD_INPUT)))
                .andExpect(jsonPath("$.errors",  is(Arrays.asList(DataInputError.EMAIL_PATTERN_MESSAGE))));


    }

    @Test
    public void AuthController_SignUp_Bad_Password() throws Exception {
        signUpDTO.setPassword("hello");
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                         .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(DataInputError.BAD_INPUT)))
                .andExpect(jsonPath("$.errors",  is(Arrays.asList(DataInputError.PASSWORD_PATTERN_MESSAGE))));


    }

    @Test
    public void AuthController_SignUp_Bad_Phone_Number() throws Exception {
        signUpDTO.setPhone("01dlad4545445");
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(DataInputError.BAD_INPUT)))
                .andExpect(jsonPath("$.errors",  containsInAnyOrder(DataInputError.PHONE_SIZE_MESSAGE,DataInputError.PHONE_PATTERN_MESSAGE)));


    }

    @Test
    public void AuthController_SignUp_All_Fiels_Required() throws Exception {
         this.signUpDTO= new SignUpDTO();
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(DataInputError.BAD_INPUT)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                DataInputError.EMAIL_NOT_BLANK_MESSAGE,
                DataInputError.PASSWORD_NOT_BLANK_MESSAGE,
                DataInputError.PSEUDO_NOT_BLANK_MESSAGE,
                DataInputError.PHONE_NOT_BLANK_MESSAGE
        )));
    }

    @Test
    public void AuthController_SignUp_UserAleadyExist() throws Exception {
        doThrow(new UserExistException(AuthErrorCste.USER_ALREADY_EXIST)).when(authService).signUp(signUpDTO);
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is(AuthErrorCste.USER_ALREADY_EXIST)));


    }




    @Test
    public void AuthController_SignIn_Fail_User_Not_Found() throws Exception {
        when(authService.signIn(authFormDTO)).thenThrow(new UsernameNotFoundException(AuthErrorCste.UNKNONW_USER+authFormDTO.getEmail()));
        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authFormDTO)))
                .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.message", is(AuthErrorCste.UNKNONW_USER+authFormDTO.getEmail())));

    }

    @Test
    public void AuthController_SignIn_Fail_Bad_Credential() throws Exception {
        when(authService.signIn(authFormDTO)).thenThrow(new BadCredentialsException(AuthErrorCste.BAD_CREDENTIAL));
        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authFormDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is(AuthErrorCste.BAD_CREDENTIAL)));

    }

    @Test
    public void AuthController_SignIn_Fail_Account_Not_Validated() throws Exception {
        when(authService.signIn(authFormDTO)).thenThrow(new AccountNotValidYetException(AuthErrorCste.ACCOUNT_NOT_YET_VALIDE));
        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authFormDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is(AuthErrorCste.ACCOUNT_NOT_YET_VALIDE)));

    }

    @Test
    public void AuthController_SignIn_Success() throws Exception{

        when(authService.signIn(authFormDTO)).thenReturn(jwtResponse);
        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authFormDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.acces_token", is(jwtResponse.getAccess_token())));

    }





}











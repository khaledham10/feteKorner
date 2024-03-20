package dz.opt.feteKorner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.controller.Impl.AuthControllerImpl;
import dz.opt.feteKorner.controller.api.AuthController;
import dz.opt.feteKorner.dto.SignUpDTO;
import dz.opt.feteKorner.exception.UserExistException;
import dz.opt.feteKorner.service.intrf.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest  {
    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private SignUpDTO signUpDTO;

    private  ObjectMapper objectMapper;

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

    }


    @Test
    public void AuthController_SignUp_Success() throws Exception {
        Mockito.doNothing().when(authService).signUp(this.signUpDTO);
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
                .andExpect(jsonPath("$.message", is("Les données saisies ne sont pas correctes")))
                .andExpect(jsonPath("$.errors",  is(Arrays.asList("Email incorrect"))));


    }

    @Test
    public void AuthController_SignUp_Bad_Password() throws Exception {
        signUpDTO.setPassword("hello");
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                         .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Les données saisies ne sont pas correctes")))
                .andExpect(jsonPath("$.errors",  is(Arrays.asList("Le mot de passe doit contenir au moins 8 caractères," +
                        " inclure au moins un chiffre et une lettre majuscule"))));


    }

    @Test
    public void AuthController_SignUp_Bad_Phone_Number() throws Exception {
        signUpDTO.setPhone("01dlad");
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Les données saisies ne sont pas correctes")))
                .andExpect(jsonPath("$.errors",  is(Arrays.asList("Le numéro doit contenir 10 caractères","Le numéro de téléphone ne peut contenir que des chiffres"))));


    }

    @Test
    public void AuthController_SignUp_All_Fiels_Required() throws Exception {
         this.signUpDTO= new SignUpDTO();
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                "L'email ne peut pas être vide",
                "Le mot de passe ne peut pas être vide",
                "Le pseudo ne peut pas être vide",
                "Le numéro de téléphone ne peut pas être vide"
        )));
    }

    @Test
    public void AuthController_SignUp_UserAleadyExist() throws Exception {
        doThrow(new UserExistException("Adresse email déja utilisée")).when(authService).signUp(signUpDTO);
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Adresse email déja utilisée")));

    }



}

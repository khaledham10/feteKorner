package dz.opt.feteKorner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.opt.feteKorner.config.JwtService;
import dz.opt.feteKorner.controller.api.AuthController;
import dz.opt.feteKorner.controller.api.ViewController;
import dz.opt.feteKorner.cste.AuthErrorCste;
import dz.opt.feteKorner.dto.AuthFormDTO;
import dz.opt.feteKorner.dto.SignUpDTO;
import dz.opt.feteKorner.exception.AccountAlreadyValidatedException;
import dz.opt.feteKorner.exception.ExpiredVerificationLinkException;
import dz.opt.feteKorner.exception.UserExistException;
import dz.opt.feteKorner.service.intrf.AuthService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doThrow;

@WebMvcTest(ViewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ViewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;


    @MockBean
    private JwtService jwtService;



    @Test
    public void ViewsController_SignIn_Fail_Link_Verification_Expired() throws Exception {
        String verifCode="ekodedekdkdeokfoefijdfojsmfjdsfs6544d4fs4dfs";

        doThrow(new ExpiredVerificationLinkException(AuthErrorCste.VERIFICATION_LINK_EXPIRED)).when(authService).verification(verifCode);
        // MockMvc test for ExpiredVerificationLinkException handler method
        mockMvc.perform(MockMvcRequestBuilders.get("/views/auth/verification")
                        .param("code", verifCode))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.view().name("linkExpired"));
    }

    @Test
    public void ViewsController_SignIn_Success() throws Exception {
        // Given
        String verificationCode = "your_verification_code"; // Replace with a valid verification code

        // When
        Mockito.doNothing().when(authService).verification(verificationCode);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/views/auth/verification")
                        .param("code", verificationCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("validation"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("loginUrl"))
                .andReturn().getModelAndView();

    }

    @Test
    public void ViewsController_SignIn_Account_Already_Validated() throws Exception {
        // Given
        String verificationCode = "yojdjfi4ndfjdfdf87777!!**:fdfjd8787f87d8f7d8f7...."; // Replace with a valid verification code

        // When
        doThrow(new AccountAlreadyValidatedException(AuthErrorCste.ACCOUNT_ALREADY_VALIDATED)).when(authService).verification(verificationCode);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/views/auth/verification")
                        .param("code", verificationCode))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.view().name("compteValidated"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("loginUrl"))
                .andReturn().getModelAndView();

    }
}

package dz.opt.feteKorner.util.impl;

import dz.opt.feteKorner.util.api.ProcessMail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class ProcessMailImpl implements ProcessMail {

    @Value("${app.url}")
    private String appUrl;

    private final MailSender mailSender;


    @Override
    public void SendVerificationMail(String email,String verificationCode,String pseudo) {
        String validationLink= this.appUrl+"/api/views/auth/verification?code="+verificationCode;
        String body= "Bonjour  "+ pseudo + "\n"+
                      """  
                                     
                        Veuillez valider votre inscription FETEKORNER  en cliquant sur le lien ci-dessous
                        """ +
                        validationLink
                      +"""
                     
                        L'équipe FETEKORNER
                        
                        """;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hamrani.khaled1997@gmail.com");
        message.setSubject("FETEKORNER_NO_REPLY");
        message.setText(body);
        mailSender.send(message);

    }
}

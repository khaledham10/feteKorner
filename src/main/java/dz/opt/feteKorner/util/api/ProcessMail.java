package dz.opt.feteKorner.util.api;

public interface ProcessMail {

    public void SendVerificationMail(String email,String verificationCode,String pseudo);
}

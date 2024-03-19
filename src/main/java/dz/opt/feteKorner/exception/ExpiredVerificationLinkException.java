package dz.opt.feteKorner.exception;

public class ExpiredVerificationLinkException extends RuntimeException{


    public  ExpiredVerificationLinkException(String msg){
        super(msg);
    }
}

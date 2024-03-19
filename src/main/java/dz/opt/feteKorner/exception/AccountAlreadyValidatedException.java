package dz.opt.feteKorner.exception;

public class AccountAlreadyValidatedException extends RuntimeException {

    public  AccountAlreadyValidatedException(String  msg){
        super(msg);
    }
}

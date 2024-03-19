package dz.opt.feteKorner.exception;

public class UserExistException extends RuntimeException{

    public UserExistException(String msg){
        super(msg);
    }
}

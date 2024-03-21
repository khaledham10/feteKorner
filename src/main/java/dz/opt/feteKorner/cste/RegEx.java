package dz.opt.feteKorner.cste;

public class RegEx {
    public static final String EMAIL_PATTERN_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_PATTERN_REGEX = "^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d).{8,}$";
    public static final String PHONE_PATTERN_REGEX = "^[0-9]+$";
}

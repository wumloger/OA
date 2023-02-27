package top.wuml.oa.service.exception;

public class LoginException extends RuntimeException{
    public LoginException(String msg) {
        System.out.println(msg);
    }
}

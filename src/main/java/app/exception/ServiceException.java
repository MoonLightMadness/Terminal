package app.exception;

public class ServiceException extends Exception {

    public ServiceException(String code,String msg){
        super(code+" "+msg);
    }

}

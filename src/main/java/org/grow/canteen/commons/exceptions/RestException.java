package org.grow.canteen.commons.exceptions;



import lombok.Getter;
import org.grow.canteen.constants.ErrorCode;

@Getter
public class RestException extends RuntimeException{

    private final String code;
    private final String message;

    public RestException(String code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }

    public RestException(ErrorCode errorCode){
        super((errorCode.getMessage()));
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
    }



}

package com.snaker.framework.exception;


import com.snaker.framework.enums.ResultEnums;

/**
 * @author pm
 */
public class BizRuntimeException extends RuntimeException {
    private String errMsg;
    private ResultEnums resultCode;

    public BizRuntimeException(ResultEnums resultCode) {
        super(resultCode.getMsg());
        this.errMsg = resultCode.getMsg();
        this.resultCode = resultCode;
    }

    public BizRuntimeException(ResultEnums resultCode, String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
        this.resultCode = resultCode;
    }

    public BizRuntimeException(ResultEnums resultCode, String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.resultCode = resultCode;
    }

    public ResultEnums getResultCode() {
        return resultCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}

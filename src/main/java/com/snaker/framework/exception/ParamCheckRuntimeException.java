package com.snaker.framework.exception;


import com.snaker.framework.enums.ResultEnums;

/**
 * @author pm
 */
public class ParamCheckRuntimeException extends RuntimeException {
    private String errMsg;
    private ResultEnums resultCode;

    public ParamCheckRuntimeException(ResultEnums resultCode) {
        super(resultCode.getMsg());
        this.errMsg = resultCode.getMsg();
        this.resultCode = resultCode;
    }

    public ParamCheckRuntimeException(ResultEnums resultCode, String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
        this.resultCode = resultCode;
    }

    public ParamCheckRuntimeException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
        this.resultCode = ResultEnums.VERIFY_CODE_ERROR;
    }

    public ResultEnums getResultCode() {
        return resultCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}

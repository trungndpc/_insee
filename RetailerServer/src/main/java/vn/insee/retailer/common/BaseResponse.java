package vn.insee.retailer.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private int error = ErrorCode.SUCCESS;
    private Object data;
    private String msg;

    public BaseResponse() {
    }

    public BaseResponse(int error) {
        this.error = error;
    }

    public BaseResponse(int error, Object data) {
        this.error = error;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

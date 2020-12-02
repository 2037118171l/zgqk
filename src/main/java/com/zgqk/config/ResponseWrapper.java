package com.zgqk.config;

/**
 * @ClassName: ResponseWrapper
 * @Description: 接口统一返回格式
 * @Author: zsl
 * @Date: 2020/8/1 15:14
 * @Version: v1.0
 */
public class ResponseWrapper {
    /**是否成功*/
    private boolean success;
    /**返回码*/
    private Integer code;
    /**返回信息*/
    private String msg;
    /**返回数据*/
    private Object data;


    /**
     * 自定义返回结果
     * 建议使用统一的返回结果，特殊情况可以使用此方法
     * @param success
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseWrapper markCustom(boolean success, Integer code, String msg, String data){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(success);
        responseWrapper.setCode(code);
        responseWrapper.setMsg(msg);
        responseWrapper.setData(data);
        return responseWrapper;
    }

    /**
     * 参数为空或者参数格式错误
     * @return
     */

    public static ResponseWrapper markParamError(){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(ReturnCode.PARAMS_ERROR.getCode());
        responseWrapper.setMsg(ReturnCode.PARAMS_ERROR.getMsg());
        return responseWrapper;
    }

    /**
     * 查询失败
     * @return
     */
    public static ResponseWrapper markError(){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(ReturnCode.FEAILED.getCode());
        responseWrapper.setMsg(ReturnCode.FEAILED.getMsg());
        responseWrapper.setData(null);
        return responseWrapper;
    }

    /**
     * 查询成功但无数据
     * @return
     */
    public static ResponseWrapper markSuccessButNoData(){
        ResponseWrapper responseWrapper  = new ResponseWrapper();
        responseWrapper.setSuccess(true);
        responseWrapper.setCode(ReturnCode.NODATA.getCode());
        responseWrapper.setMsg(ReturnCode.NODATA.getMsg());
        responseWrapper.setData(null);
        return responseWrapper;
    }

    /**
     * 查询成功且有数据
     * @param data
     * @return
     */
    public static ResponseWrapper markSuccess(Object data){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(true);
        responseWrapper.setCode(ReturnCode.SUCCESS.getCode());
        responseWrapper.setMsg(ReturnCode.SUCCESS.getMsg());
        responseWrapper.setData(data);
        return  responseWrapper;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
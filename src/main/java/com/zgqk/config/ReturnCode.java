package com.zgqk.config;

/**
 * @ClassName: ReturnCode
 * @Description: 接口返回码和返回值
 * 结合返回数据封装类ResponseWrapper，统一接口的数据返回格式
 * @Author: guohui
 * @Date: 2020/8/1 15:15
 * @Version: v1.0
 */
public enum ReturnCode {

        SUCCESS(200,"操作成功"),
        NODATA(204,"查询成功无记录"),
        FEAILED(400,"操作失败"),
        API_NOT_EXISTS(404, "请求的接口不存在"),
        API_NOT_PER(203, "没有该接口的访问权限"),
        PARAMS_ERROR(400, "参数为空或格式错误"),
        SIGN_ERROR(401, "数据签名错误"),
        UNKNOWN_IP(305, "非法IP请求"),
        SYSTEM_ERROR (501, "系统异常");

        private final Integer code;
        private final String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ReturnCode(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }


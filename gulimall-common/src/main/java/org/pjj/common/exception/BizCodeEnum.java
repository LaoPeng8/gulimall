package org.pjj.common.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为 5 为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：10001。 10:通用  001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 * 10: 通用
 * 001：参数格式校验
 * 11: 商品
 * 12: 订单
 * 13: 购物车
 * 14: 物流
 *
 *
 * 所有的枚举类型隐性地继承自 java.lang.Enum。
 * 枚举实质上还是类!而每个被枚举的成员实质就是一个枚举类型的实例，
 * 他们默认都是public static final修饰的。可以直接通过枚举类型名使用它们。
 *
 */
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    VAILD_EXCEPTION(10001, "参数格式校验失败"),
    PARAMETER_EMPTY_EXCEPTION(11002, "方法传入参数为空");

    private int code;
    private String msg;
    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}

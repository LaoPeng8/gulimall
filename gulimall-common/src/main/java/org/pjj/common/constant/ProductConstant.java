package org.pjj.common.constant;

/**
 * 商品服务相关的常量
 * @author PengJiaJun
 * @Date 2022/06/10 00:56
 */
public class ProductConstant {

    public enum AttrEnum{
        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性");

        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
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

}

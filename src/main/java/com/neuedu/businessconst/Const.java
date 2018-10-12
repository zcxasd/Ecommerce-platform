package com.neuedu.businessconst;

public class Const {
    public static final String CURRENTUSER = "user";

    public static final String USERNAMECOOKIE = "username";
    public static final String PASSWORDCOOKIE = "password";

    public static final String AUTOLOGINTOKEN = "auto_login_token";

    public static final String EXCEPTION = "ex";
    public static final String TOKEN_PREFIX = "token_";

    public enum ProductCode{

        ILLEGAL_PARAM(101,"参数错误");

        private int code;
        private String msg;

        private ProductCode(int code, String msg){
            this.code=code;
            this.msg=msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum STOCK{

        LIMIT_NUM_SUCCESS("LIMIT_NUM_SUCCESS"),
        LIMIT_NUM_FALL("LIMIT_NUM_FALL");

        private String stockdesc;
        private STOCK(String stockdesc){
            this.stockdesc=stockdesc;
        }

        public String getStockdesc() {
            return stockdesc;
        }

        public void setStockdesc(String stockdesc) {
            this.stockdesc = stockdesc;
        }
    }

    public enum CHECKENUM{
        CHECKED(1),
        UNCHECKED(0);
        private int code;
        private CHECKENUM(int code){
            this.code=code;
        }
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum ALIPAYSTATUS{

        //WAIT_BUYER_PAY	交易创建，等待买家付款 10
        //TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款 60
        //TRADE_SUCCESS	交易支付成功 20
        //TRADE_FINISHED 交易结束，不可退款 50
        WAIT_BUYER_PAY(10,"WAIT_BUYER_PAY"),
        TRADE_CLOSED(60,"TRADE_CLOSED"),
        TRADE_SUCCESS(20,"TRADE_SUCCESS"),
        TRADE_FINISHED(50,"TRADE_FINISHED");
        private int code;
        private String msg;

        ALIPAYSTATUS(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
            }
        }
    public enum PAYPLATFORM{
        ALIPAY(1,"alipay"),
        WEIXIN(2,"weixin");
        private Integer code;
        private String msg;

        PAYPLATFORM(Integer code,String msg) {
            this.code=code;
            this.msg = msg;
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
    }

    public enum PAYONLINEOROFFLINE{
        ONLINE_PAY(1,"线上支付"),
        OFFLINE_PAY(2,"线下支付");
        private int code;
        private String msg;

        PAYONLINEOROFFLINE(int code,String msg) {
            this.code = code;
            this.msg=msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
    /*订单状态枚举类型*/
    public enum ORDERSTATUSENUM{
        ORDER_CANCELLED(0,"已取消"),
        ORDER_NO_PAY(10,"未付款"),
        ORDER_PAY(20,"已付款"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCCESS(50,"交易成功"),
        ORDER_CLOSE(60,"交易关闭");


        private int code;
        private String msg;

        ORDERSTATUSENUM(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public static ORDERSTATUSENUM codeOf(int status){
            for (ORDERSTATUSENUM orderstatusenum:ORDERSTATUSENUM.values()){
                if (orderstatusenum.getCode()==status){
                    return orderstatusenum;
                }
            }
            return null;
        }
    }
}

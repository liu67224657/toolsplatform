package com.enjoyf.activity.util.sendsns;

/**
 * Created by zhimingli on 2016/8/9 0009.
 */
public class SMSSenderSngl {
    private static SMSsender smSsender;

    public static SMSsender get() {
        if (smSsender == null) {
            synchronized (SMSSenderSngl.class) {
                if (smSsender == null) {
                    smSsender = new BFLTSMSsender();
                }
            }
        }
        return smSsender;
    }


    //使用示例
    public static void main(String[] args) {
        SMSsender smSsender = SMSSenderSngl.get();
        String validCode = "验证码：" + "测试短信" + System.currentTimeMillis() + "，欢迎来到着迷网【着迷网】";
        SendResult sendResult = smSsender.sendMessage("15652914865", validCode);
        System.out.println(sendResult.toString());
    }
}

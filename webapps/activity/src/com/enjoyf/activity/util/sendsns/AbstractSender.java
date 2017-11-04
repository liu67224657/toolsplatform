package com.enjoyf.activity.util.sendsns;

/**
 * Created by zhimingli on 2016/8/9 0009.
 */
public abstract class AbstractSender implements SMSsender {
    @Override
    public SendResult sendMessage(String phone, String content) {
        SendResult sendResult = null;
        try {
            sendResult = sendAction(phone, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sendResult == null) {
            System.out.println(this.getClass().getName() + "send result is null.phone: " + phone);
            sendResult = new SendResult();
            sendResult.setCode(SendResult.SEND_ERROR);
            sendResult.setMsg("ssend.result.null");
        }

        return sendResult;
    }

    protected abstract SendResult sendAction(String phone, String content);
}

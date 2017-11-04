package com.enjoyf.activity.controller.weixinop.qyz;

import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.facade.GoodsItemFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.RespTextMessage;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *青云志领码
 * @author huazhang
 *
 */
public class QyzGoodsItemProcessor implements WeixinDataProcessor {
	private static final Logger logger = LoggerFactory.getLogger(QyzGoodsItemProcessor.class);

	private GoodsItemFacade goodsItemFacade=new GoodsItemFacade();

	private static SimpleDateFormat dayFormat=new SimpleDateFormat("yyyyMMdd");

	private static SimpleDateFormat monthFormat=new SimpleDateFormat("yyyyMM");

	private static final String APPID = "wx529ba0bd21499c1a";


	@Override
	public String processRequest(Map<String, String> requestMap) {
		String respMessage = "";

		String respContent = ""; //默认回复

		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");

		String goodsId=requestMap.get("goodsId");
		// 回复文本消息
		RespTextMessage textMessage = new RespTextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);


		StringBuffer sb = new StringBuffer();

		if (StringUtil.isEmpty(goodsId)) {
			sb.append("活动未开启");
			respContent = sb.toString();
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			return respMessage;
		}

		Date date=new Date();
        /*
         * 检查用户是否在本月已领取过
         */
		Set<String> isReceivedGoodsItemSet=goodsItemFacade.isReceivedGoodsItem(APPID, Long.valueOf(goodsId), fromUserName, monthFormat.format(date));
		if (!CollectionUtil.isEmpty(isReceivedGoodsItemSet)) {//本月已领取过激活码
			for (String exchangeTime : isReceivedGoodsItemSet) {
				if (exchangeTime.contains(monthFormat.format(date))) {
					sb.append("少侠本月已经来过了哦，给别人一点机会吧！请继续关注公众号，还有其他福利在等着你呦。");
					respContent = sb.toString();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
			}
		}

        /*
         * 检查当天是否还有剩余激活码可领取
         */
		boolean hasSurplusGoodsItems=goodsItemFacade.hasSurplusGoodsItem(APPID, Long.valueOf(goodsId), dayFormat.format(date));
		if (!hasSurplusGoodsItems) {
			sb.append("少侠您来的太晚了，今日礼包已被洗劫一空，请明天早些来抢呦。");
			respContent = sb.toString();
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			return respMessage;
		}

		GoodsItem goodsItem;
		try {
			goodsItem = goodsItemFacade.getGoodsItem(APPID, Long.valueOf(goodsId), fromUserName, 200, dayFormat.format(date), Contants.GOODS_CATEGORY_DESPATCH);
			if (null == goodsItem) {
				sb.append("少侠您来的太晚了，今日礼包已被洗劫一空，请明天早些来抢呦。");
				respContent = sb.toString();
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}

			sb.append("少侠今天运气不错，来的很及时啊，青云君赏赐礼包一个：");
			sb.append(goodsItem.getGoodsItemValue());
			sb.append("更多礼包也会不定期更新，少侠记得每天来看下呦。");
			respContent = sb.toString();
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);

			return respMessage;

		} catch (JoymeDBException e) {
			logger.error("QyzGoodsItemProcessor.processRequest exception:"+e.getStackTrace());
		}

		return null;
	}
}

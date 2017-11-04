package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.GoodsItemFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/goodsUpload")
@Controller
public class QyzGoodsUploadController extends BaseRestSpringController {

    private static GoodsFacade goodsFacade = new GoodsFacade();
    private static GoodsItemFacade goodsItemFacade = new GoodsItemFacade();


    /**
     * 跳转到生成商品页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView showUploadPage(HttpServletRequest request,
                                       HttpServletResponse response) throws JoymeDBException, IOException, JoymeServiceException {
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile uplodFile = multipartRequest.getFile("goodsItemFile");
        boolean addFlag = true;
        Map<String, Object> mapMessage = Maps.newHashMap();
        String goodsName = request.getParameter("goodsName");
        String requireScore = request.getParameter("requireScore");
        String imageUrl = request.getParameter("imageUrl");
        String goodsType = request.getParameter("goodsType");
        String gameId = request.getParameter("gameId");
        String totalNum = request.getParameter("totalNum");
        String expireTime = request.getParameter("expireTime");
        String description = request.getParameter("description");
        String goodsItemStr = request.getParameter("goodsItems");

        String[] goodsItemArray = goodsItemStr.split("\\r\\n");

        int total = Integer.parseInt(totalNum);
        Goods goods = goodsFacade.queryGoodsByName(goodsName);
        if (goods == null) {
            goods = new Goods();
        } else {
            addFlag = false;
        }
        goods.setGoodsName(goodsName);
        goods.setExchangeNum(0);
        goods.setTotalNum(total);
        goods.setSurplusNum(total);
        goods.setImagePath(imageUrl);
        goods.setCreateTime(new Date());
        goods.setDescription(description);
        goods.setStatus(ValidStatus.VALID);
        goods.setExpireTime(DateUtil.StringTodate(expireTime, DateUtil.PATTERN_DATE_TIME));
        goods.setGoodCategory(Contants.GOODS_CATEGORY_EXCHANGE);
        goods.setGoodType(goodsType);
        goods.setRequireScore(Integer.parseInt(requireScore));
        goods.setGameId(gameId);
        goodsFacade.saveGoods(goods);
        Goods savedGoods = goodsFacade.queryGoodsByName(goodsName);

        //goodid 存一份到redis
        if (savedGoods != null) {
            goodsFacade.setGoodsId(savedGoods.getGoodsId(), savedGoods);
        }


        if (goodsType.equals("V") && addFlag) {
            for (String itemId : goodsItemArray) {
                if(StringUtil.isEmpty(itemId.trim())){
                    continue;
                }
                GoodsItem goodsItem = new GoodsItem();
                goodsItem.setGameId(savedGoods.getGameId());
                goodsItem.setGoodsItemValue(itemId.trim());
                goodsItem.setGoodsId(savedGoods.getGoodsId());
                goodsItem.setCreateTime(new Date());
                goodsItem.setStatus(ValidStatus.VALID.getCode());
                goodsItemFacade.saveGoodsItem(goodsItem);

                GoodsItem insertGoodsItem = goodsItemFacade.getGoodsItemByGameitemvalue(savedGoods.getGoodsId(), itemId.trim());
                if (insertGoodsItem != null) {
                    goodsItemFacade.pushGoodsItem(insertGoodsItem.getGoodsId(), insertGoodsItem);
                }
            }

            // 判断文件是否为空
//            if (!uplodFile.isEmpty()) {
//                // 文件保存路径
//                String path = request.getSession().getServletContext().getRealPath("upload");
//                String fileName = uplodFile.getOriginalFilename();
//                File targetFile = new File(path, fileName);
//                if (!targetFile.exists()) {
//                    targetFile.mkdirs();
//                }
//                // 转存文件
//                uplodFile.transferTo(targetFile);
//                File codeFile = new File(path, fileName);
//                BufferedReader reader = null;
//                try {
//                    reader = new BufferedReader(new FileReader(codeFile));
//                    String tempString = null;
//                    int line = 1;
//                    // 一次读入一行，直到读入null为文件结束
//                    while ((tempString = reader.readLine()) != null) {
//                        if (!StringUtil.isEmpty(tempString)) {
//
//
//                            GoodsItem goodsItem = new GoodsItem();
//                            goodsItem.setGameId(savedGoods.getGameId());
//                            goodsItem.setGoodsItemValue(tempString.trim());
//                            goodsItem.setGoodsId(savedGoods.getGoodsId());
//                            goodsItem.setCreateTime(new Date());
//                            goodsItem.setStatus(ValidStatus.VALID.getCode());
//                            goodsItemFacade.saveGoodsItem(goodsItem);
//
//
//                            GoodsItem insertGoodsItem = goodsItemFacade.getGoodsItemByGameitemvalue(savedGoods.getGoodsId(), tempString.trim());
//                            if (insertGoodsItem != null) {
//                                goodsItemFacade.pushGoodsItem(insertGoodsItem.getGoodsId(), insertGoodsItem);
//                            }
//                        }
//                    }
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                            codeFile.delete();
//                        } catch (IOException e1) {
//                        }
//                    }
//                }
//            }
        }

        return new ModelAndView("/jsp/qyz/goodsUploadResult", mapMessage);
    }

}

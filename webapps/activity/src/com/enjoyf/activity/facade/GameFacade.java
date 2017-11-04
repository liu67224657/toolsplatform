package com.enjoyf.activity.facade;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.qyz.Game;
import com.enjoyf.activity.cache.GameRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.GameService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class GameFacade {
    private static  String activityPrefix = "activity-qyzActivity";
    private GameService gameService = new GameService();
    private GameRedis gameRedis = new GameRedis(PropertiesContainer.getRedisManager());
    private GoodsFacade goodsFacade = new GoodsFacade();
    private GoodsItemFacade goodsItemFacade = new GoodsItemFacade();
    public Map<String,String> getGameList(String activityPrefix, String openid,String activityCode) throws JoymeServiceException, JoymeDBException {
        Map<String,String> map = new HashMap<String,String>();
        String firstGame = this.getGameLevel(activityPrefix+ "_"+openid + "_" + "first","1",activityCode,openid) ;
        boolean firstFlag = false;
        if (!StringUtil.isEmpty(firstGame)){
            map.put("firstGame",firstGame);
            firstFlag = true;
        }else {
            map.put("firstGame","-1");
        }
        String secondGame = this.getGameLevel(activityPrefix+ "_"+openid + "_" + "second","2",activityCode,openid) ;
        boolean secondFlag = false;
        if (!StringUtil.isEmpty(secondGame)){
            secondFlag = true;
            map.put("secondGame",secondGame);
        }else {
            map.put("secondGame","-1");
        }
        String thirdGame = this.getGameLevel(activityPrefix+ "_"+openid + "_" + "third","3",activityCode,openid) ;
        boolean thirdFlag = false;
        if (!StringUtil.isEmpty(thirdGame)){
            thirdFlag = true;
            map.put("thirdGame",thirdGame);
        }else {
            map.put("thirdGame","-1");
        }
        //TODO 获取cdk码
        if (firstFlag && secondFlag && thirdFlag){
            Goods goods = goodsFacade.getGoods(Contants.QYZ_APPID,2l);
            GoodsItem goodsItem = goodsItemFacade.getGoodsItemForQyz(Contants.QYZ_APPID,goods.getGoodsId(),openid,Contants.GOODS_CATEGORY_DESPATCH);
            if (goodsItem == null){
                map.put("cdkCode","少侠你来晚了，奖励已经领完！");
            }else {
                map.put("cdkCode",goodsItem.getGoodsItemValue());
            }
        }else {
            map.put("cdkCode","-1");
        }
        return map;
    }
    public void saveGame(String gameLevel, String openid,String gameId,String redisKey) throws JoymeServiceException, JoymeDBException {
        String redisValue = PropertiesContainer.getRedisManager().get(redisKey);
        if (StringUtil.isEmpty(redisValue)){
            Game game = gameService.queryGamebyId(null,gameId);
            if (game == null){
                game = new Game();
                game.setCreateTime(new Date());
                game.setGameLevel(gameLevel);
                game.setProfileId(openid);
                game.setGameId(gameId);
                gameService.insertGame(null,game);
                gameRedis.setGame(redisKey,gameLevel);
            }
        }
    }
    private String getGameLevel(String key,String gameLevel,String activityCode,String openid) throws JoymeServiceException, JoymeDBException {
        String gameId = MD5Util.Md5(openid+activityCode+gameLevel);
        String redisGameLevel = gameRedis.getGameLevel(key);
        if (StringUtil.isEmpty(redisGameLevel)){
            Game game = gameService.queryGamebyId(null,gameId);
            if (game != null){
                gameRedis.setGame(key,game.getGameLevel());
                return game.getGameLevel();
            }
            return null;
        }
        return redisGameLevel;
    }
}


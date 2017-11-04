package com.enjoyf.wiki.bean;

import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/5/20 15:03
 * Description:
 */
public class CardInfo {
    private String cardName;
    private String cardImage;
    private Map<String, String> cardMap;


    public CardInfo(String cardName, String cardImage, Map<String, String> cardMap) {
        this.cardName = cardName;
        this.cardImage = cardImage;
        this.cardMap = cardMap;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public Map<String, String> getCardMap() {
        return cardMap;
    }

    public void setCardMap(Map<String, String> cardMap) {
        this.cardMap = cardMap;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}

package com.androidhive.xmlparsing.news;

import java.util.ArrayList;
import java.util.List;

import model.NewsFeedObject;

/**
 * Created by user on 7/5/13.
 */
public class NewsSourceObject {

   private List<NewsFeedObject> handlerList = new ArrayList<NewsFeedObject>();
   private int sourcePlace;


    public NewsSourceObject(List<NewsFeedObject> handlerList, int sourcePlace){
        this.handlerList = handlerList;
        this.sourcePlace = sourcePlace;
    }

    public List<NewsFeedObject> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<NewsFeedObject> handlerList) {
        this.handlerList = handlerList;
    }

    public int getSourceArrayPlace() {
        return sourcePlace;
    }

    public void setSourceArrayPlace(int sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

}

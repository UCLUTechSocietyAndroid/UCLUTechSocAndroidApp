package rss_parser;


import java.util.List;

import model.NewsFeedObject;

public interface NewsFeedsHandler {

    public List<NewsFeedObject> getNewsFeedObjects(String newsFeed);

}

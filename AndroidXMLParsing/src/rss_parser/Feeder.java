package rss_parser;

import java.util.List;

import com.uclutech.model.NewsFeedObject;

public interface Feeder {

	public List<NewsFeedObject> getAll();
	
}

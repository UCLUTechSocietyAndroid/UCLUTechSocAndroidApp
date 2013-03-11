package com.androidhive.xmlparsing;




public class NewsFeedObject {

	private String title;
	private String bigTitle;
	private String date;
	private String content;
	private String contentUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBigTitle() {
		return bigTitle;
	}

	public void setBigTitle(String bigTitle) {
		this.bigTitle = bigTitle;
	}

	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@Override
	public String toString() {
		String titleString = "title: " + title + "\n";
		String dateString = "date: " + date + "\n";
		String contentString = "content: " + content + "\n";
		String contentURLString = "URL: " + contentUrl.toString() + "\n";

		return titleString + dateString + contentString + contentURLString;
	}

}
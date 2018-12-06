package it.windtre.tremobilitycms.ui.entities;

public class PageInfo {
	private final String link;
	private final String icon;
	private final String title;
	private String collection;

	public PageInfo(String link, String icon, String title, String collection) {
		this.link = link;
		this.icon = icon;
		this.title = title;
		this.collection = collection;
	}

	public String getLink() {
		return link;
	}

	public String getIcon() {
		return icon;
	}

	public String getTitle() {
		return title;
	}

	public String getCollection() { return collection; }
}

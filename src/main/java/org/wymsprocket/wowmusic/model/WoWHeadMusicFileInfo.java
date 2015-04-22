package org.wymsprocket.wowmusic.model;

public class WoWHeadMusicFileInfo implements Comparable<WoWHeadMusicFileInfo> {

	private int id;
	private String title;
	private String url;
	private String type;
	private int zoneId;
	private Long crc = Long.valueOf(0);

	public WoWHeadMusicFileInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public Long getCrc() {
		return crc;
	}

	public void setCrc(Long crc) {
		this.crc = crc;
	}

	@Override
	public int compareTo(WoWHeadMusicFileInfo woWHeadMusicFileInfo) {
		return getTitle().compareTo(woWHeadMusicFileInfo.getTitle());
	}

	@Override
	public String toString() {
		return String.format("%d,%s,%s,%s,%d,%d", getId(), getTitle(), getUrl(), getType(), getZoneId(), getCrc());
	}
}

package org.wymsprocket.wowmusic.model;

import org.wymsprocket.wowmusic.util.WoWHeadMappingsProperties;

import com.google.common.collect.ComparisonChain;

public class WoWHeadZoneInfo implements Comparable<WoWHeadZoneInfo> {

	private int id;
	private String name;
	private int category;
	private int expansion;
	private int heroicLevel;
	private int instance;
	private int lfgReqLevel;
	private int maxlevel;
	private int minlevel;
	private int nplayers;
	private int reqlevel;
	private int territory;
	private final static WoWHeadMappingsProperties wowheadMappingsProperties = new WoWHeadMappingsProperties();

	public WoWHeadZoneInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getExpansion() {
		return expansion;
	}

	public void setExpansion(int expansion) {
		this.expansion = expansion;
	}

	public int getHeroicLevel() {
		return heroicLevel;
	}

	public void setHeroicLevel(int heroicLevel) {
		this.heroicLevel = heroicLevel;
	}

	public int getInstance() {
		return instance;
	}

	public void setInstance(int instance) {
		this.instance = instance;
	}

	public int getLfgReqLevel() {
		return lfgReqLevel;
	}

	public void setLfgReqLevel(int lfgReqLevel) {
		this.lfgReqLevel = lfgReqLevel;
	}

	public int getMaxlevel() {
		return maxlevel;
	}

	public void setMaxlevel(int maxlevel) {
		this.maxlevel = maxlevel;
	}

	public int getMinlevel() {
		return minlevel;
	}

	public void setMinlevel(int minlevel) {
		this.minlevel = minlevel;
	}

	public int getNplayers() {
		return nplayers;
	}

	public void setNplayers(int nplayers) {
		this.nplayers = nplayers;
	}

	public int getReqlevel() {
		return reqlevel;
	}

	public void setReqlevel(int reqlevel) {
		this.reqlevel = reqlevel;
	}

	public int getTerritory() {
		return territory;
	}

	public void setTerritory(int territory) {
		this.territory = territory;
	}

	public boolean hasContinent() {
		return Boolean.parseBoolean((String) wowheadMappingsProperties.get("continent." + getCategory()));
	}

	@Override
	public int compareTo(WoWHeadZoneInfo wowHeadZone) {
		return ComparisonChain.start()
				.compare(getExpansion(), wowHeadZone.getExpansion())
				.compare(getInstance(), wowHeadZone.getInstance())
				.compare(getCategory(), wowHeadZone.getCategory())
				.compare(getName(), wowHeadZone.getName())
				.result();

	}

	@Override
	public String toString() {
		return String.format("%d,%s,%s,%s,%s",
				getId(),
				getName(),
				wowheadMappingsProperties.getExpansionName(getExpansion()),
				wowheadMappingsProperties.getType(getInstance()),
				wowheadMappingsProperties.getContinentName(getCategory()));
	}
}

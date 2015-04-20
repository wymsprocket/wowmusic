package org.wymsprocket.wowmusic.model;

public class WoWHeadZone {

	public enum Type {
		ZONE("Zone"),
		DUNGEON("Dungeon"),
		RAID("Raid"),
		BATTLEGROUND("Battleground"),
		SCENARIO("Scenario"),
		UNKNOWN("Unknown");

		private String name;

		Type(String name) {
			this.name = name;
		}

		public static Type getType(String name) {
			for (Type type : values()) {
				if (type.name.equalsIgnoreCase(name)) {
					return type;
				}
			}
			return Type.UNKNOWN;
		}
	}

	private int id;
	private String name;
	private Type type;
	private String continent;
	private int location;
	private String expansion;

	public WoWHeadZone() {
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	@Override
	public String toString() {
		return String.format("%d,%s,%s,%s,%d,%s", getId(), getName(), getExpansion(), getContinent(), getLocation(), getType());
	}
}

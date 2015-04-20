package org.wymsprocket.wowmusic.model;

public class WoWHeadZone {

    public enum Type {
        ZONE,
        DUNGEON,
        RAID,
        BATTLEGROUND,
        SCENARIO;
    }

    private String name;
    private Type type;
    private String continent;
    private String location;
    private String expansion;

    public WoWHeadZone(String name) {
        this.name = name;
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


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

}

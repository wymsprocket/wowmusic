package org.wymsprocket.wowmusic.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;

public class WoWHeadMappingsProperties extends Properties {

	/**
	 *
	 */
	private static final long serialVersionUID = -4800131456670620668L;

	public WoWHeadMappingsProperties() {
		InputStream inputStream = WoWHeadZoneInfo.class.getClassLoader().getResourceAsStream("wowhead-mappings.properties");
		try {
			load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getExpansionName(int id) {
		return (String) get("expansion-name." + id);
	}

	public String getContinentName(int id) {
		return (String) get("continent-name." + id);
	}

	public String getType(int id) {
		return (String) get("type." + id);
	}

    public String getZoneExpansion(int id) {
        return (String) get("zone-expansion." + id);
    }

	public String getZoneContinent(int id) {
	    return (String) get("zone-continent." + id);
	}
}

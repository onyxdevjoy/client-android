package com.onyx.international.models;

import java.util.ArrayList;

public class Gift {

	public int id;
	public ArrayList<String> imageArray;
	public int thumbImageSizeWidth;
	public int thumbImageSizeHeight;
	public int remainCount;
	public int exchangeCount;
	public int wishCount;
	public String name;
	public String description;
	public String gameName;
	public String gameRequired;
	public int gameId;
	public boolean isExchanged;
	public boolean isCollect;
	
	public Gift() {
		imageArray = new ArrayList<String>();
	}
}

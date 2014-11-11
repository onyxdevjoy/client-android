package com.onyx.international.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class GiftModel {

	public Map<Integer, Gift> giftMap;
	public ArrayList<Integer> mainPageGift;
	public ArrayList<Integer> collectGiftArray;
	public ArrayList<Integer> exchangeGiftArray;
	public int currentExchangedGiftId;
	
	public Integer mCurrentSelectGiftId;
	
	private static GiftModel instance = null;
	
	public static GiftModel sharedModel() {
		if (instance == null) {
			instance = new GiftModel();
		}
		return instance;
	}
	
	@SuppressLint("UseSparseArrays")
	private GiftModel() {
		giftMap = new HashMap<Integer, Gift>();
		mainPageGift = new ArrayList<Integer>();
		collectGiftArray = new ArrayList<Integer>();
		exchangeGiftArray = new ArrayList<Integer>();
	}
	
	public Gift getGift(int giftId) {
		return giftMap.get(Integer.valueOf(giftId));
	}
	
	public void addOrUpdateGift(Gift gift) {
		giftMap.put(Integer.valueOf(gift.id), gift);
	}
	
	public void addMainPageGift(Gift gift) {
		addOrUpdateGift(gift);
		
		for (Integer integer : mainPageGift) {
			if (integer.intValue() == gift.id) {
				return;
			}
		}
		
		mainPageGift.add(Integer.valueOf(gift.id));
	}
	
	public void addCollectGift(Gift gift) {
		addOrUpdateGift(gift);
		
		for (Integer integer : collectGiftArray) {
			if (integer.intValue() == gift.id) {
				return;
			}
		}
		
		collectGiftArray.add(Integer.valueOf(gift.id));
	}
	
	public void deleteCollectGfit(int giftId) {
		int index = -1;
		for (int i = 0; i < collectGiftArray.size(); ++ i) {
			Integer integer = collectGiftArray.get(i);
			if (integer.intValue() == giftId) {
				index = i;
				break;
			}
		}
		
		if (index >= 0) {
			collectGiftArray.remove(index);
		}
	}
	
	public void addExchangedGift(Gift gift) {
		addOrUpdateGift(gift);
		
		for (Integer integer : exchangeGiftArray) {
			if (integer.intValue() == gift.id) {
				return;
			}
		}
		
		exchangeGiftArray.add(Integer.valueOf(gift.id));
	}
	
	public static Gift parseGiftFromJson(JSONObject object) throws JSONException {
		Gift gift = new Gift();
		gift.id = object.has("gift_id") ? object.getInt("gift_id") : 0;
		gift.name = object.has("gift_name") ? object.getString("gift_name") : "Unknown";
		gift.remainCount = object.has("num") ? object.getInt("num") : 0;
		gift.gameId = object.has("game_id") ? object.getInt("game_id") : 0;
		gift.gameRequired = object.has("cond") ? object.getString("cond") : "Unknown";
		gift.description = object.has("desc") ? object.getString("desc") : "Unknown";
		if (object.has("image")) {
			String image = object.getString("image");
			String images[] = image.split(",");
			for (int i=0; i<images.length; ++i) {
				gift.imageArray.add("http://kaolagift.qiniudn.com/" + images[i]);	
			}
		}
		gift.thumbImageSizeWidth = object.has("image_width") ? object.getInt("image_width") : 0;
		gift.thumbImageSizeHeight = object.has("image_height") ? object.getInt("image_height") : 0;
		gift.isCollect = object.has("is_collect") && object.getInt("is_collect") == 1 ? true : false;
		gift.isExchanged = object.has("is_exchange") && object.getInt("is_exchange") == 1 ? true : false;
		gift.exchangeCount = object.has("exchange_num") ? object.getInt("exchange_num") : 0;
		return gift;
	}
}

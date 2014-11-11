package com.onyx.international.models;

import java.util.ArrayList;

public class UserModel {

	public ArrayList<AddressInfo> addressInfoArray;
	public String bindPhoneNumber;
	public String tmpPhoneNumber;
	public String tmpCaptchaCode;
	
	private static UserModel instance = null;
	
	public static UserModel sharedModel() {
		if (instance == null) {
			instance = new UserModel();
		}
		return instance;
	}
	
	private UserModel() {
		addressInfoArray = new ArrayList<AddressInfo>();
	}
	
	public void clearAddressInfo() {
		addressInfoArray.clear();
	}
	
	public AddressInfo getAddressInfo(int id) {
		for (AddressInfo info : addressInfoArray) {
			if (info.id == id) {
				return info;
			}
		}
		return null;
	}
	
	public boolean addAddressInfo(AddressInfo newInfo) {
		for (AddressInfo info : addressInfoArray) {
			if (info.id == newInfo.id) {
				addressInfoArray.remove(info);
				break;
			}
		}
		
		addressInfoArray.add(newInfo);
		
		return true;
	}
	
	public void deleteAddressInfo(int id) {
		for (AddressInfo info : addressInfoArray) {
			if (info.id == id) {
				addressInfoArray.remove(info);
				break;
			}
		}
	}
	
	public void clearData() {
		bindPhoneNumber = "";
		
		
	}
}

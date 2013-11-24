package com.github.cloudm.utils;


/**
 * çŠ¶æ?æšä¸¾ç±?
 * 
 * @author andy.wan
 *
 */
public enum Status {
	
	available(0), unavailable(1);
	
	private int value;
	
	public int value() {
		return value;
	}

	Status(int value) {
		this.value = value;
	}
	
	public static Status fromKey(Integer key) {
		for (Status status : Status.values()) {
			if (key.intValue() == status.value) {
				return status;
			}
		}
		return null;
	}

}

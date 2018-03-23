package com.databilize.fastcascade;

import java.util.List;

public class Level1Data {
	private String name;
	private List<Level2Data> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Level2Data> getData() {
		return data;
	}

	public void setData(List<Level2Data> data) {
		this.data = data;
	}

}

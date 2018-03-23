package com.databilize.fastcascade;

import java.util.List;

public class Level2Data {
	private String name;
	private List<Level3Data> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Level3Data> getData() {
		return data;
	}

	public void setData(List<Level3Data> data) {
		this.data = data;
	}

}

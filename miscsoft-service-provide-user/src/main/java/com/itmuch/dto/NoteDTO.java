package com.itmuch.dto;

import com.github.pagehelper.Page;
import com.itmuch.model.Note;

public class NoteDTO extends Note{
	
	
	private String categrayName;
	
	

	public String getCategrayName() {
		return categrayName;
	}

	public void setCategrayName(String categrayName) {
		this.categrayName = categrayName;
	}
	
}

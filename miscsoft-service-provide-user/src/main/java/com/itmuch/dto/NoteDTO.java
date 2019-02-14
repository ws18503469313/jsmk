package com.itmuch.dto;

import java.util.List;

import com.itmuch.model.Note;
import com.itmuch.model.NoteDetail;

public class NoteDTO extends Note{
	
	
	private String categrayName;
	
	private List<NoteDetail> details;
	

	public String getCategrayName() {
		return categrayName;
	}

	public void setCategrayName(String categrayName) {
		this.categrayName = categrayName;
	}

	public List<NoteDetail> getDetails() {
		return details;
	}

	public void setDetails(List<NoteDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "NoteDTO [categrayName=" + categrayName + ", details=" + details + "]";
	}
	
}

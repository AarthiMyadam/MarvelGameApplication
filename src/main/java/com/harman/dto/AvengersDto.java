package com.harman.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvengersDto {
	
	private String name;
	private List<Character> character;
	
	
	public AvengersDto() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Character> getCharacter() {
		return character;
	}


	public void setCharacter(List<Character> character) {
		this.character = character;
	}


	

	


	

	


	
	
	
	
}
	


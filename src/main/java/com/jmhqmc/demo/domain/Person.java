package com.jmhqmc.demo.domain;

import java.io.Serializable; 

public class Person implements Serializable{

	private static final long serialVersionUID = -8087556529728393626L;

	private String id;

	private String username;

	private Integer age;

	public Person() {

	}

	public Person(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
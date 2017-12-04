package com.my.project.soap.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private int id;
	private String username;
	private String nickname;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User) {
			User u = (User)obj;
			if(this.username.equals(u.getUsername())
				&& this.password.equals(u.getPassword())
				&& this.nickname.equals(u.getNickname())) {
				return true;
			}
		}
		return false;
	}

}

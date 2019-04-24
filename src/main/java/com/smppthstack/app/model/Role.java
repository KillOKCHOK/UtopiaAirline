package com.smppthstack.app.model;

//import org.springframework.security.core.GrantedAuthority;

public enum Role /*implements GrantedAuthority*/{
	USER, ADMIN, AGENT, ANAUTHORIZED, COUNTER_IN_CHARGE, TRAVELLER;

	/*@Override
	public String getAuthority() {
		return name();
	}*/
}

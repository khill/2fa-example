/**
 * 
 */
package org.khill.authservices.dto;

/**
 * Represents a user within the authentication services
 * 
 * @author khill
 *
 */
public class User {

	private String username;

	private String secret;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}

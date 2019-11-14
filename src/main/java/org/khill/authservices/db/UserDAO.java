/**
 * 
 */
package org.khill.authservices.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.khill.authservices.dto.User;

/**
 * Data access object for interacting with user persistence
 * 
 * @author khill
 *
 */
@Repository
public class UserDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	@Qualifier("userJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Stores the user into the persistence layer
	 * 
	 * @param user		the user to be stored
	 */
	public void storeUser(User user) {
		// check to see if user exists
		String sql = "select username from auth_2fa_users where username = ?";
		boolean exists = this.getJdbcTemplate().query(sql,  new Object[] {user.getUsername()}, rs -> {
			return rs.next();
		});
		if(!exists) {
			sql = "insert into auth_2fa_users (username, secret_2fa_code) values "
				+ "(?, ?)";
			this.getJdbcTemplate().update(sql, user.getUsername().toLowerCase(), user.getSecret());
		} else {
			sql = "update auth_2fa_users set secret_2fa_code = ? where username = ?";
			this.getJdbcTemplate().update(sql, user.getSecret(), user.getUsername().toLowerCase());
		}
	}
	
	/**
	 * Loads the user with the given username
	 * 
	 * @param username		the username of the desired user
	 * @return	the User associated with the username or null if no match is found
	 */
	public User loadUserByUsername(String username) {
		User user = null;
		if(username != null) {
			String sql = "select secret_2fa_code from auth_2fa_users where username = ?";
			user = this.getJdbcTemplate().query(sql, new Object[] {username.toLowerCase()}, rs -> {
				User u = null;
				if(rs.next()) {
					u = new User();
					u.setUsername(username);
					u.setSecret(rs.getString("secret_2fa_code"));
				}
				return u;
			});
		}
		return user;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}

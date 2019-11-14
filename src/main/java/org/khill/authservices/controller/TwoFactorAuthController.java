/**
 * 
 */
package org.khill.authservices.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.khill.authservices.db.UserDAO;
import org.khill.authservices.dto.User;

/**
 * Controller for handling two factor authentication functionality
 * 
 * @author khill
 *
 */
@RestController
@RequestMapping("/2fa")
public class TwoFactorAuthController {

	private static final String APP_NAME = "2fa-example";
	
	private static final String ISSUER = "prototypes.khill.org";
	
	private static final Logger LOG = LoggerFactory.getLogger(TwoFactorAuthController.class);
	
	private static final String QR_PREFIX = 
			  "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value = "/setup/{username}", method = RequestMethod.GET)
	public boolean setupTwoFactorAuthentication(@PathVariable String username) {
    boolean result = false;
    try {
		  User user = new User();
		  user.setUsername(username);
		  // generate a new secret for the user
		  user.setSecret(Base32.random());
		  // store the secret in the database
		  this.getUserDao().storeUser(user);
      result = true;
    } catch (Exception e) {
      LOG.warn("Error occurred setting up two factor authentication", e);
    }
    return result;
	}
	
	@RequestMapping(value = "/verify/{username}/{code}", method = RequestMethod.GET)
	public boolean verifyTwoFactorAuthenticationCode(@PathVariable String username, @PathVariable("code") String verificationCode) {
		boolean verified = false;
		User user = this.getUserDao().loadUserByUsername(username);
		if(user != null) {
			try {
				Totp totp = new Totp(user.getSecret());
				verified = totp.verify(verificationCode);
			} catch (Exception e) {
				LOG.warn("Error occurred verifying code", e);
			}
		}
		return verified;
	}
	
	@RequestMapping(value = "/qrcode/{username}", method = RequestMethod.GET)
	public String getQRCodeURL(@PathVariable String username) throws UnsupportedEncodingException {
		User user = this.getUserDao().loadUserByUsername(username);
		if(user != null) {
	        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", 
	        		APP_NAME, user.getUsername(), user.getSecret(), ISSUER), "UTF-8");					
		} else {
			return null;
		}
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


}

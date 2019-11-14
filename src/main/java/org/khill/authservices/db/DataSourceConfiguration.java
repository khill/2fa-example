/**
 * 
 */
package org.khill.authservices.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Configuration of the data source
 * 
 * @author khill
 *
 */
@Configuration
public class DataSourceConfiguration {

	@Autowired
	private Environment env;

	@Bean(name = "userDb")
	public DataSource userDataSource() {
		DriverManagerDataSource userDS = new DriverManagerDataSource();
		userDS.setDriverClassName(env.getProperty("datasource.driver-class-name"));
		userDS.setUrl(env.getProperty("datasource.jdbc-url"));
		userDS.setUsername(env.getProperty("datasource.username"));
		userDS.setPassword(env.getProperty("datasource.password"));
		return userDS;
	}

	@Bean(name = "userJdbcTemplate")
	public JdbcTemplate userJdbcTemplate(@Qualifier("userDb")DataSource ds) {
		return new JdbcTemplate(ds);
	}
}

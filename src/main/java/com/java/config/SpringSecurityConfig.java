package com.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.java.service.BookService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BookService bookService;

//	@Autowired
//	private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}


//	Định cấu hình bảo mật của các đường dẫn web trong ứng dụng, đăng nhập, đăng nhập, v.v.
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Các trang không yêu cầu login
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

		// Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
		// Nếu chưa login, nó sẽ redirect tới trang /login.
		// nếu chỉ có 1 role trong database thì sử dụng HASROLE  còn nếu có nhiều role thì sủ dụng HASANYROLE 
		http.authorizeRequests().antMatchers("/checkout" ).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// Trang chỉ dành cho ADMIN
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//
				// Submit URL của trang login
//					Biểu mẫu đăng nhập sẽ truyền dữ liệu lên URL này để xử lý (user&password)
				.loginProcessingUrl("/doLogin") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/")//
				.successHandler(new SuccessHandler()).failureUrl("/login?error=true")//
				.usernameParameter("customerId")//
				// Cấu hình cho Logout Page.
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

		http.rememberMe().rememberMeParameter("remember"); // [remember-me]

	}
}

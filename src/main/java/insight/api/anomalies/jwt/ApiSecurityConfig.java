package insight.api.anomalies.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import insight.api.anomalies.serviceImpl.UserAuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private ApiAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/signin", "/signup");
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable();
		http.cors();
		http.authorizeRequests().antMatchers("/signin", "/signup").permitAll()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/**
		 * give authorization for user to manage Solution api
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/solutions").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/solutions/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/solutions").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/solutions").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/solutions/**").hasAnyAuthority("ADMIN");
		/**
		 * villes api
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/villes").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/villes/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/villes").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/villes").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/villes/**").hasAnyAuthority("ADMIN");
		/**
		 * probleme api
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/problemes").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/problemes/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/problemes").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/problemes").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/problemes/**").hasAnyAuthority("ADMIN");
		/**
		 * devices api
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/devices").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/devices/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/devices").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/devices").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/devices/**").hasAnyAuthority("ADMIN");
		/**
		 * products api
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/products").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/products/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/products").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/products").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/products/**").hasAnyAuthority("ADMIN");
		/** 
		 * reclamations api 
		 */
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/Api/reclamations").hasAnyAuthority("ADMIN", "USER") // get 
		.antMatchers(HttpMethod.GET,"/Api/reclamations/**").hasAnyAuthority("ADMIN", "USER") // get 
	    .antMatchers(HttpMethod.POST, "/Api/reclamations").hasAnyAuthority("ADMIN") // post
	    .antMatchers(HttpMethod.PUT, "/Api/reclamations").hasAnyAuthority("ADMIN")  // put
	    .antMatchers(HttpMethod.DELETE, "/Api/reclamations/**").hasAnyAuthority("ADMIN");
		/**
		 * Download and upload files to server 
		 * 
		 */
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/upload").hasAnyAuthority("ADMIN", "USER");
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/download").hasAnyAuthority("ADMIN");
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/Api/checks/**").hasAnyAuthority("ADMIN", "USER");
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/Api/checks/all").hasAnyAuthority("ADMIN", "USER");
	
		
	}	
	@Bean
	public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter filter) {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
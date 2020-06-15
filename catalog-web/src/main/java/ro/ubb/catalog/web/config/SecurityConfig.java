package ro.ubb.catalog.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import ro.ubb.catalog.core.model.UserRole;
import ro.ubb.catalog.web.security.CatalogUserDetailsService;
import ro.ubb.catalog.web.security.MySavedRequestAwareAuthenticationSuccessHandler;
import ro.ubb.catalog.web.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan("ro.ubb.catalog.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler
            mySavedRequestAwareAuthenticationSuccessHandler;

    @Autowired
    private CatalogUserDetailsService catalogUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(authProvider());
//        auth.inMemoryAuthentication()
//                .withUser("teacher").password("pass").roles("TEACHER")
//                .and()
//                .withUser("student").password("pass").roles("STUDENT");
    }


    @Override
    protected UserDetailsService userDetailsService() {
        return catalogUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/rents").hasRole(UserRole.TEACHER.toString())
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(mySavedRequestAwareAuthenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();

         */
        http
                .cors();
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/rents").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/rents/**").hasRole("ADMIN")
                //.antMatchers("/api/rents").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/movies").permitAll()
                .antMatchers(HttpMethod.POST, "/api/movies").permitAll()
                .antMatchers(HttpMethod.GET,"/api/clients").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/movies").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/movies").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/clients").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/clients").permitAll()
                .antMatchers("/api/login").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return mySavedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}


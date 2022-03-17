//package com.payMyBuddy.config;
//
//import com.payMyBuddy.services.UserService;
//import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//@EnableEncryptableProperties
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserService userService;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    public SecurityConfig(@Lazy UserService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/signup.html")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .and()
//                .logout()
//                .permitAll();
//
//        http.formLogin()
//                .defaultSuccessUrl("/home", true);
//
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login");
//    }
//
//    @Override
//    public void configure(WebSecurity webSecurity) throws Exception {
//        webSecurity.ignoring()
//                .antMatchers("/resources/**");
//    }
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        return tokenRepository;
//    }
//
//}

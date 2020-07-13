package org.sid.cinema_proj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        /* Indiquer à SpringSecurity que l’authentification passe par un formulaire d’authentification avec username et
password */
        http.formLogin().loginPage("/login");
        /* Toutes les requites HTTP avec URL /save/* ... nécessitent d’être authentifié avec un utilisateur ayant le role ADMIN */
        http.authorizeRequests().antMatchers("/save**/**","/edit**/**","/delete**/**","/generate**/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/list**/**").hasRole("USER");
        /* Toutes les requettes doivent nécessitent d'etre authentifié*/
       // http.authorizeRequests().anyRequest().authenticated();
        /* Si un Utilisateur tente d’accéder à une resource dont il n’a pas le droit, il sera redirigué vers la page associée
à l’action /notAuthorized */
        http.exceptionHandling().accessDeniedPage("/notAuthorized");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

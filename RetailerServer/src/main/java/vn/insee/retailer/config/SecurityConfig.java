package vn.insee.retailer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import vn.insee.retailer.filter.AuthenticationFilter;
import vn.insee.retailer.security.InseeUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationFilter cookieAuthenticationFilter;

    @Autowired
    private InseeUserDetailService inseeUserDetailService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .antMatchers("/api/admin/statistical/**").permitAll()
//                .antMatchers(("/api/utility/**")).permitAll()
//                .antMatchers("/api/admin/authen/**").permitAll()
//                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inseeUserDetailService);
    }

}

package com.example.task_2.config;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired

    private  JWTGenerator jwtGenerator;
    @Autowired

    private CustomUserDetails customUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    String token= getJwtFromRequset(request);

        String username="";
    if(StringUtils.hasText(token) && jwtGenerator.validateToken(token))
    {
        username= jwtGenerator.getUsernameFromJwt(token);

        UserDetails userDetails= customUserDetails.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }


        filterChain.doFilter(request,response);
    }


    public String getJwtFromRequset(HttpServletRequest request){
        String token= request.getHeader("Authorization");
        if (StringUtils.hasText(token) &&token.startsWith("Bearer")){
            return token.substring(7,token.length());
        }
        return null;
}
}
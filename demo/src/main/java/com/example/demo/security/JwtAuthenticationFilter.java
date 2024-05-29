package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    private SysUserService sysUserService;

    private static final String[] URL_PERMITTED_LIST = {
            "/api/login",
            "/api/register"
    };

    public JwtAuthenticationFilter(@Autowired AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String token = request.getHeader("token");

        if (Arrays.asList(URL_PERMITTED_LIST).contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        CheckResult checkResult = JwtTokenUtil.validateToken(token);
        if (!checkResult.isSuccess()) {
            switch (checkResult.getErrCode) {
                case JWTConstant.JWT_ERRCODE_NULL:
                    throw new JwtException("token不存在");
                case JWTConstant.JWT_ERRCODE_EXPIRE:
                    throw new JwtException("token已过期");
                case JWTConstant.JWT_ERRCODE_FAIL:
                    throw new JwtException("token认证过期");

            }
        }

        Claims claims = checkResult.getClaims();
        String username = claims.getSubject();
        SysUser sysUser = sysUserService.getByUserName(username);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null,
                new ArrayList<GrantedAuthority>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}

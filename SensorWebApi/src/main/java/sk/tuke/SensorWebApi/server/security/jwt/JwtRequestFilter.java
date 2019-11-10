package sk.tuke.SensorWebApi.server.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.tuke.SensorWebApi.server.security.services.UserProviderService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter
{
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer ";
    private static final int JWT_INDEX = 7;

    @Autowired
    private UserProviderService userProviderService;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException
    {
        final String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        String userName = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_TOKEN)) {
            jwt = authorizationHeader.substring(JWT_INDEX);
            userName = jwtProvider.getUsername(jwt);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userProviderService.loadUserByUsername(userName);
            if (jwtProvider.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

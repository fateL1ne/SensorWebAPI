package sk.tuke.SensorWebApi.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.security.jwt.JwtProvider;
import sk.tuke.SensorWebApi.server.security.jwt.JwtRequest;
import sk.tuke.SensorWebApi.server.security.jwt.JwtResponse;
import sk.tuke.SensorWebApi.server.security.services.UserProviderService;

@RestController
@CrossOrigin
public class JwtController
{
    private static Logger logger = LoggerFactory.getLogger(JwtController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserProviderService userProviderService;

    @Autowired
    private JwtProvider jwtProvider;

    @RequestMapping(value = "/authJWT", method = RequestMethod.POST)
    public ResponseEntity<?> createJWT(@RequestBody JwtRequest jwtRequest) throws Exception
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Wrong credentials");
        }

        final UserDetails userDetails = userProviderService.loadUserByUsername(jwtRequest.getUsername());
        final String newJWT = jwtProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(newJWT));
    }
}

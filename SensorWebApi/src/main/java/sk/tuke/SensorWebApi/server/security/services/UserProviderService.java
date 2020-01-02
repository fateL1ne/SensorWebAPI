package sk.tuke.SensorWebApi.server.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Persona;
import sk.tuke.SensorWebApi.server.jpa.repositories.UserRepository;

import java.util.ArrayList;

@Service
public class UserProviderService implements UserDetailsService
{

    // TODO: 11/10/19 IMPLEMENT ROLES

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Persona persona = userRepository.findByUsername(s);
        return new User(persona.getUsername(), persona.getPassword(), new ArrayList<>());
    }
}

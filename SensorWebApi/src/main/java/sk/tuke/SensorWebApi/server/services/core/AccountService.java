package sk.tuke.SensorWebApi.server.services.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.request.NewAccount;
import sk.tuke.SensorWebApi.server.http.response.AccountsResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Persona;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.UserRepository;
import sk.tuke.SensorWebApi.server.security.SecurityConfiguration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService
{
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;

    @Autowired
    public AccountService(UserRepository userRepository, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
    }

    @Transactional
    public AccountsResponse fetchAll() {
        List<Persona> all = userRepository.findAll();

        // return only accounts that are active (not lazy deleted)
        return new AccountsResponse(all.stream().filter(Persona::isActive).collect(Collectors.toList()));
    }

    @Transactional
    public ResponseEntity createAccount(NewAccount newAccount) {
        if(!newAccount.isValid()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //if desk with this label already exists
        if(userRepository.findByUsername(newAccount.getUsername()) != null) {
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
        }

        if(newAccount.getUsername().length() > 29 ||
                newAccount.getFirstName().length() > 29 ||
                newAccount.getLastName().length() > 29 ||
                newAccount.getPassword().length() > 59) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Persona newDbPersona = new Persona();
        newDbPersona.setUsername(newAccount.getUsername());

        // get password encoder from security context
        PasswordEncoder passwordEncoder = securityConfiguration.getPasswordEncoder();
        // hash password
        newDbPersona.setPassword(passwordEncoder.encode(newAccount.getPassword()));

        newDbPersona.setFirstName(newAccount.getFirstName());
        newDbPersona.setLastName(newAccount.getLastName());
        newDbPersona.setActive(true);

        userRepository.save(newDbPersona);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity deleteAccount(Long accountId) {
        Optional<Persona> account = userRepository.findById(accountId);

        if(!account.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // lazy delete account from database
        account.get().setActive(false);

        userRepository.save(account.get());

        return new ResponseEntity(HttpStatus.OK);
    }
}

package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.http.request.NewAccount;
import sk.tuke.SensorWebApi.server.http.response.AccountsResponse;
import sk.tuke.SensorWebApi.server.services.core.AccountService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AccountController
{
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public AccountsResponse getAllAccounts() {
        return accountService.fetchAll();
    }

    @PostMapping(value = "/create")
    public ResponseEntity createAccount(@RequestBody NewAccount newAccount) {
        return accountService.createAccount(newAccount);
    }

    @GetMapping(value = "/delete/{accountId}")
    public ResponseEntity createAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId);
    }
}

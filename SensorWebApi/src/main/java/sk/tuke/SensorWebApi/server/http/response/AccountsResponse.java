package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Persona;

import java.util.ArrayList;
import java.util.List;

public class AccountsResponse
{
    private final List<AccountResponse> accounts = new ArrayList<>();

    public AccountsResponse(List<Persona> accounts) {
        accounts.forEach(account -> this.accounts.add(new AccountResponse(account)));
    }

    public List<AccountResponse> getAccounts() {
        return accounts;
    }
}

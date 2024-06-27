package animores.pet_service.account.service;


import animores.pet_service.account.entity.Account;

public interface AccountService {
    Account getAccount();
    Account getAccountFromContext();
}

package com.midas.app.services;

import com.midas.app.models.Account;
import com.midas.generated.model.UpdateAccountDto;
import java.util.List;

public interface AccountService {
  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(Account details);

  Account updateAccount(String accountId, UpdateAccountDto updateAccountDto);

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  List<Account> getAccounts();
}

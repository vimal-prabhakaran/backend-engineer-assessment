package com.midas.app.builder;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEntityBuilder implements Builder<Account, CreateAccount> {

  @Override
  public Account build(CreateAccount createAccount) {
    return Account.builder()
        .firstName(createAccount.getFirstName())
        .lastName(createAccount.getLastName())
        .email(createAccount.getEmail())
        .build();
  }
}

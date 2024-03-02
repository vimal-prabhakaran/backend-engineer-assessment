package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import io.temporal.spring.boot.ActivityImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@ActivityImpl(taskQueues = {"create-account-workflow", "update-account-workflow"})
public class AccountActivityImpl implements AccountActivity {

  private final PaymentProvider paymentProvider;

  private final AccountRepository accountRepository;

  /**
   * saveAccount saves an account in the data store.
   *
   * @param account is the account to be saved
   * @return Account
   */
  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  /**
   * createPaymentAccount creates a payment account in the system or provider.
   *
   * @param account is the account to be created
   * @return Account
   */
  @Override
  public Account createPaymentAccount(Account account) {
    return paymentProvider.createAccount(account);
  }

  /**
   * updatePaymentAccount updated an existing payment account in the system or provider.
   *
   * @param account is the account to be updated
   * @return Account
   */
  @Override
  public Account updatePaymentAccount(Account account) {
    return paymentProvider.updateAccount(account);
  }
}

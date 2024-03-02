package com.midas.app.providers.external.stripe;

import com.midas.app.builder.AccountEntityBuilder;
import com.midas.app.enums.PaymentProviderType;
import com.midas.app.exceptions.GlobalExceptionHandler;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class StripePaymentProvider implements PaymentProvider {
  private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);

  private final StripeConfiguration configuration;
  private final AccountEntityBuilder accountEntityBuilder;
  private final GlobalExceptionHandler globalExceptionHandler;

  /** providerName is the name of the payment provider */
  @Override
  public String providerName() {
    return PaymentProviderType.STRIPE.getName();
  }

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(Account details) {
    setApiKeyInContext();
    try {
      CustomerCreateParams params =
          CustomerCreateParams.builder()
              .setName(getFullName(details))
              .setEmail(details.getEmail())
              .build();
      Customer customer = Customer.create(params);
      details.setProviderType(PaymentProviderType.STRIPE);
      details.setProviderId(customer.getId());
    } catch (StripeException exception) {
      logger.error("StripeException was thrown while creating a customer", exception);
      globalExceptionHandler.handleAll(exception, null);
    }
    return details;
  }

  /**
   * updateAccount updates an existing account in the payment provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  @Override
  public Account updateAccount(Account details) {
    setApiKeyInContext();
    try {
      Customer existingCustomer = Customer.retrieve(details.getProviderId());
      if (Objects.isNull(existingCustomer)) return createAccount(details);
      CustomerUpdateParams params =
          CustomerUpdateParams.builder()
              .setName(getFullName(details))
              .setEmail(details.getEmail())
              .build();
      existingCustomer.update(params);
      return details;
    } catch (StripeException exception) {
      logger.error("StripeException was thrown while updating a customer", exception);
    }
    return null;
  }

  private void setApiKeyInContext() {
    Stripe.apiKey = configuration.getApiKey();
  }

  private String getFullName(Account account) {
    return account.getFirstName() + " " + account.getLastName();
  }
}

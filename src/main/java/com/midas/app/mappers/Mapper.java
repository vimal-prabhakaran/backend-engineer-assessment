package com.midas.app.mappers;

import com.midas.app.models.Account;
import com.midas.generated.model.AccountDto;
import java.util.Objects;
import lombok.NonNull;

public class Mapper {
  // Prevent instantiation
  private Mapper() {}

  /**
   * toAccountDto maps an account to an account dto.
   *
   * @param account is the account to be mapped
   * @return AccountDto
   */
  public static AccountDto toAccountDto(@NonNull Account account) {
    var accountDto = new AccountDto();

    accountDto
        .id(account.getId())
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .email(account.getEmail())
        .providerType(
            Objects.nonNull(account.getProviderType()) ? account.getProviderType().getName() : null)
        .providerId(account.getProviderId())
        .createdAt(account.getCreatedAt())
        .updatedAt(account.getUpdatedAt());

    return accountDto;
  }
}

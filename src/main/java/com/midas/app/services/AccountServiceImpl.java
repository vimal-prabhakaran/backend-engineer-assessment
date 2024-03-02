package com.midas.app.services;

import com.midas.app.exceptions.GlobalExceptionHandler;
import com.midas.app.exceptions.ResourceNotFoundException;
import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.CreateAccountWorkflow;
import com.midas.app.workflows.UpdateAccountWorkflow;
import com.midas.generated.model.UpdateAccountDto;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Workflow;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final Logger logger = Workflow.getLogger(AccountServiceImpl.class);

  private final WorkflowClient workflowClient;

  private final AccountRepository accountRepository;

  private final GlobalExceptionHandler exceptionHandler;

  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(Account details) {
    var options =
        WorkflowOptions.newBuilder()
            .setTaskQueue(CreateAccountWorkflow.QUEUE_NAME)
            .setWorkflowId(details.getEmail())
            .build();

    logger.info("initiating workflow to create account for email: {}", details.getEmail());

    var workflow = workflowClient.newWorkflowStub(CreateAccountWorkflow.class, options);

    return workflow.createAccount(details);
  }

  /**
   * updateAccount updates the existing account in the system or provider.
   *
   * @param accountId is the id of the account to be updated
   * @param updateAccountDto is the details of the account to be updated.
   * @return Account
   */
  @Override
  public Account updateAccount(String accountId, UpdateAccountDto updateAccountDto) {

    Optional<Account> existingAccountOptional =
        accountRepository.findById(UUID.fromString(accountId));
    if (existingAccountOptional.isEmpty())
      exceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException());
    Account existingAccount = existingAccountOptional.get();
    if (Objects.nonNull(updateAccountDto.getFirstName())) {
      existingAccount.setFirstName(updateAccountDto.getFirstName());
    }
    if (Objects.nonNull(updateAccountDto.getLastName())) {
      existingAccount.setLastName(updateAccountDto.getLastName());
    }
    if (Objects.nonNull(updateAccountDto.getEmail())) {
      existingAccount.setEmail(updateAccountDto.getEmail());
    }

    var options =
        WorkflowOptions.newBuilder()
            .setTaskQueue(UpdateAccountWorkflow.QUEUE_NAME)
            .setWorkflowId(existingAccount.getEmail())
            .build();

    logger.info("initiating workflow to update account for email: {}", existingAccount.getEmail());

    var workflow = workflowClient.newWorkflowStub(UpdateAccountWorkflow.class, options);
    return workflow.updateAccount(existingAccount);
  }

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  @Override
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }
}

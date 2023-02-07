package eu.inparsys.examples.bank.config;

import eu.inparsys.examples.bank.account.AccountId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class AccountIdConverter implements Converter<String, AccountId> {

  @Override
  public AccountId convert(String value) {
    return new AccountId(UUID.fromString(value));
  }
}

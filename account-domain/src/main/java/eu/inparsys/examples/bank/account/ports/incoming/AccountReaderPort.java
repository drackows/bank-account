package eu.inparsys.examples.bank.account.ports.incoming;

import eu.inparsys.examples.bank.account.Account;
import eu.inparsys.examples.bank.account.AccountId;

public interface AccountReaderPort {
    Account findById(AccountId accountId);
}

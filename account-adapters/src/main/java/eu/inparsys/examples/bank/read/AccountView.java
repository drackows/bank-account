package eu.inparsys.examples.bank.read;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.CreditLine;
import eu.inparsys.examples.bank.customer.CustomerId;
import lombok.Data;
import org.javamoney.moneta.Money;

@Data
public class AccountView {
    AccountId accountId;
    CustomerId ownerId;
    Money balance;
    CreditLine creditLine;
}

package eu.inparsys.examples.bank.account.commands;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.Recipient;
import eu.inparsys.examples.bank.customer.CustomerId;
import org.javamoney.moneta.Money;

public record MakeTransactionCommand(
        CustomerId customerId,
        AccountId accountId,
        Recipient recipient,
        Money amount,
        String transferTitle
) {
}

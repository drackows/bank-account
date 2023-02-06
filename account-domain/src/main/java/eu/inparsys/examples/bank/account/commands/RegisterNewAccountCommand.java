package eu.inparsys.examples.bank.account.commands;

import eu.inparsys.examples.bank.common.command.Command;
import eu.inparsys.examples.bank.customer.CustomerId;
import org.javamoney.moneta.Money;

import java.util.Currency;

public record RegisterNewAccountCommand(
        CustomerId ownerId,
        Currency currency,
        Money initialDeposit
) implements Command {
}

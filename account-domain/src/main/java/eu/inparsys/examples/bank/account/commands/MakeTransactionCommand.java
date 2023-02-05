package eu.inparsys.examples.bank.account.commands;

import eu.inparsys.examples.bank.account.Recipient;
import org.javamoney.moneta.Money;

import java.time.LocalDate;

public record MakeTransactionCommand(
        Recipient recipient,
        Money amount,
        String transferTitle
) {
}

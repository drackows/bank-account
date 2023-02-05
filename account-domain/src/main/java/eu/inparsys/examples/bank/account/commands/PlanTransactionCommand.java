package eu.inparsys.examples.bank.account.commands;

import eu.inparsys.examples.bank.account.Recipient;
import org.javamoney.moneta.Money;

import java.time.LocalDate;

public record PlanTransactionCommand(
        Recipient recipient,
        LocalDate transferDate,
        Money amount,
        String transferTitle
) {
}

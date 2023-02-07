package eu.inparsys.examples.bank.account.commands;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.CreditLine;
import eu.inparsys.examples.bank.common.command.Command;

public record SetupCreditLineCommand(
        AccountId accountId,
        CreditLine creditLine
) implements Command {
}

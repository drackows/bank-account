package eu.inparsys.examples.bank.account.ports.incoming;

import eu.inparsys.examples.bank.account.commands.CreateNewAccountCommand;

public interface CreateNewAccount {
    void handle(CreateNewAccountCommand command);
}

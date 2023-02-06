package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.account.ports.incoming.AccountReaderPort;
import eu.inparsys.examples.bank.common.Result;

public class MakeTransactionCommandHandler {
    AccountReaderPort accountReaderPort;
    EventsPublisher eventsPublisher;

    void handle(MakeTransactionCommand command) {
        final Account account = new Account();
        final Result result = account.makeTransaction(command);
        result.events().forEach(eventsPublisher::publish);
    }
}

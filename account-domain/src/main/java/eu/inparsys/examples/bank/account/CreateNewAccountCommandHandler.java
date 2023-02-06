package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.account.ports.incoming.AccountReaderPort;
import eu.inparsys.examples.bank.common.Result;

public class CreateNewAccountCommandHandler {
    AccountReaderPort accountReaderPort;
    EventsPublisher eventsPublisher;

    void handle(RegisterNewAccountCommand command) {
        final Account account = new Account();
        final Result result = account.register(command);
        result.events().forEach(eventsPublisher::publish);
    }
}

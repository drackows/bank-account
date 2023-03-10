package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.account.ports.incoming.AccountReaderPort;
import eu.inparsys.examples.bank.common.Result;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MakeTransactionCommandHandler {
    private final AccountReaderPort accountReaderPort;
    private final EventsPublisher eventsPublisher;

    public void handle(MakeTransactionCommand command) {
        final Account account = accountReaderPort.findById(command.accountId());
        if (account.isOwnedBy(command.customerId())) {
            final Result result = account.makeTransaction(command);
            result.events().forEach(eventsPublisher::publish);
        }
    }
}

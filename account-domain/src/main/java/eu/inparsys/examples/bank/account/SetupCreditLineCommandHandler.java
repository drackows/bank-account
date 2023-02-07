package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.SetupCreditLineCommand;
import eu.inparsys.examples.bank.account.ports.incoming.AccountReaderPort;
import eu.inparsys.examples.bank.common.Result;
import eu.inparsys.examples.bank.common.command.CommandHandler;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class SetupCreditLineCommandHandler implements CommandHandler<SetupCreditLineCommand> {
    private final AccountReaderPort accountReaderPort;
    private final EventsPublisher eventsPublisher;

    @Override
    public void handle(final SetupCreditLineCommand command) {
        final Account account = accountReaderPort.findById(command.accountId());
        final Result result = account.setupCreditLine(command);
        result.events().forEach(eventsPublisher::publish);
    }
}

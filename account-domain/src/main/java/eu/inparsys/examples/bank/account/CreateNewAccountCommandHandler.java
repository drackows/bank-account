package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.common.Result;
import eu.inparsys.examples.bank.common.command.CommandHandler;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class CreateNewAccountCommandHandler implements CommandHandler<RegisterNewAccountCommand> {
    private final EventsPublisher eventsPublisher;

    public void handle(RegisterNewAccountCommand command) {
        final Account account = new Account(new AccountId());
        final Result result = account.register(command);
        result.events().forEach(eventsPublisher::publish);
    }
}

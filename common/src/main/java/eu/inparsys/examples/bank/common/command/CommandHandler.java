package eu.inparsys.examples.bank.common.command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}

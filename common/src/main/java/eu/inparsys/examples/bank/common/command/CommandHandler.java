package eu.inparsys.examples.bank.common.command;

import java.lang.reflect.ParameterizedType;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}

package eu.inparsys.examples.bank.account.requests;

import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.customer.CustomerId;

import java.util.UUID;

public class AccountsMapper {

    public static RegisterNewAccountCommand toCommand(CreateAccountRequest request) {
        if (request == null) {
            return null;
        }
        return new RegisterNewAccountCommand(
                new CustomerId(UUID.fromString(request.getOwnerId())),
                request.getCurrency(),
                request.getInitialDeposit()
        );
    }

}

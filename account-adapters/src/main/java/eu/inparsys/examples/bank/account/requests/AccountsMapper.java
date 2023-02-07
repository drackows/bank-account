package eu.inparsys.examples.bank.account.requests;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.CreditLine;
import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.account.commands.SetupCreditLineCommand;

public class AccountsMapper {

    public static RegisterNewAccountCommand registerNewAccountCommand(CreateAccountRequest request) {
        if (request == null) {
            return null;
        }
        return new RegisterNewAccountCommand(request.getOwnerId(), request.getCurrency(), request.getInitialDeposit());
    }

    public static SetupCreditLineCommand setupCreditLineCommand(final AccountId accountId, final CreditLine creditLine) {
        return new SetupCreditLineCommand(accountId, creditLine);
    }
}

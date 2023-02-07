package eu.inparsys.examples.bank.transaction.requests;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.customer.CustomerId;

import java.util.UUID;

public class TransactionsMapper {

    public static MakeTransactionCommand toDomain(final CustomerId customerId, final MakeTransactionRequest request) {
        return new MakeTransactionCommand(
                customerId,
                new AccountId(UUID.fromString(request.getAccountId())),
                request.getRecipient(),
                request.getAmount(),
                request.getTransferTitle()
        );
    }
}

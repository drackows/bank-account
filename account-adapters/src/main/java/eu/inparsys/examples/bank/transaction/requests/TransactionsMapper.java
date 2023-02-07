package eu.inparsys.examples.bank.transaction.requests;

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.customer.CustomerId;

public class TransactionsMapper {

    public static MakeTransactionCommand toDomain(final CustomerId customerId, final MakeTransactionRequest request) {
        return new MakeTransactionCommand(
                customerId,
                request.getAccountId(),
                request.getRecipient(),
                request.getAmount(),
                request.getTransferTitle()
        );
    }
}

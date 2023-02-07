package eu.inparsys.examples.bank.read;

import eu.inparsys.examples.bank.account.Recipient;
import eu.inparsys.examples.bank.transaction.TransactionId;
import lombok.Data;
import org.javamoney.moneta.Money;

@Data
public class Transaction {
    TransactionId transactionId;
    Recipient recipient;
    Money amount;
}

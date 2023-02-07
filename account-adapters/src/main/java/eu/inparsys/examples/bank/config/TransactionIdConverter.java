package eu.inparsys.examples.bank.config;

import eu.inparsys.examples.bank.transaction.TransactionId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class TransactionIdConverter implements Converter<String, TransactionId> {

    @Override
    public TransactionId convert(String value) {
        return new TransactionId(UUID.fromString(value));
    }
}

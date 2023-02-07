package eu.inparsys.examples.bank.config;

import eu.inparsys.examples.bank.customer.CustomerId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class CustomerIdConverter implements Converter<String, CustomerId> {

    @Override
    public CustomerId convert(String value) {
        return new CustomerId(UUID.fromString(value));
    }
}

package eu.inparsys.examples.bank.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.customer.CustomerId;
import eu.inparsys.examples.bank.transaction.TransactionId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Function;

@Configuration
class ObjectMapperConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        SimpleModule idsModule = new SimpleModule();
        idsModule.addDeserializer(AccountId.class, new IdDeserializer<>(AccountId::new));
        idsModule.addDeserializer(CustomerId.class, new IdDeserializer<>(CustomerId::new));
        idsModule.addDeserializer(TransactionId.class, new IdDeserializer<>(TransactionId::new));
        idsModule.addSerializer(AccountId.class, new IdSerializer<>(AccountId::getId));
        idsModule.addSerializer(CustomerId.class, new IdSerializer<>(CustomerId::getId));
        idsModule.addSerializer(TransactionId.class, new IdSerializer<>(TransactionId::getId));
        return new ObjectMapper().registerModules(new MoneyModule(), idsModule);
    }

    @RequiredArgsConstructor
    static class IdDeserializer<T> extends JsonDeserializer<T> {
        private final Function<UUID, T> idFactory;

        @Override
        public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            return idFactory.apply(UUID.fromString(deserializationContext.readValue(jsonParser, String.class)));
        }
    }

    @RequiredArgsConstructor
    static class IdSerializer<T> extends JsonSerializer<T> {
        private final Function<T, UUID> uuidFromId;

        @Override
        public void serialize(final T t, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(uuidFromId.apply(t).toString());
        }
    }
}

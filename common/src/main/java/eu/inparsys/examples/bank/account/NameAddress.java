package eu.inparsys.examples.bank.account;

import java.util.List;

public record NameAddress(
        String name,
        List<String> address //max 3 lines
) {

}

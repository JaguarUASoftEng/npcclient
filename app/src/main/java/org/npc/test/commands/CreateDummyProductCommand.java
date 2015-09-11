package org.npc.test.commands;

import org.apache.commons.lang3.RandomStringUtils;
import org.npc.test.api.models.Product;
import org.npc.test.commands.interfaces.ResultCommandInterface;

import java.util.Random;

public class CreateDummyProductCommand implements ResultCommandInterface<Product> {
    @Override
    public Product execute() {
        Random random = new Random();

        return (new Product()).
            setLookupCode(RandomStringUtils.randomAlphanumeric(PRODUCT_LOOKUP_CODE_LENGTH)).
            setCount(MIN_PRODUCT_COUNT + random.nextInt(MAX_PRODUCT_COUNT - MIN_PRODUCT_COUNT));
    }

    private static final int MIN_PRODUCT_COUNT = 50;
    private static final int MAX_PRODUCT_COUNT = 300;
    private static final int PRODUCT_LOOKUP_CODE_LENGTH = 25;
}

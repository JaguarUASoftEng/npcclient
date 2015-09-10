package org.npc.test.api.enums;

import org.npc.test.api.interfaces.PathElementInterface;

import java.util.HashMap;
import java.util.Map;

public enum ProductApiMethod implements PathElementInterface {
    NONE(""),
    PRODUCT("product"),
    PRODUCTS("products");

    @Override
    public String getPathValue() {
        return value;
    }

    public static ProductApiMethod map(String key) {
        if (valueMap == null) {
            valueMap = new HashMap<>();

            for (ProductApiMethod value : ProductApiMethod.values()) {
                valueMap.put(value.getPathValue(), value);
            }
        }

        return (valueMap.containsKey(key) ? valueMap.get(key) : ProductApiMethod.NONE);
    }

    private String value;

    private static Map<String, ProductApiMethod> valueMap = null;

    private ProductApiMethod(String value) {
        this.value = value;
    }
}

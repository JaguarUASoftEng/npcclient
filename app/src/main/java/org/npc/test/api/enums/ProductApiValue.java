package org.npc.test.api.enums;

import org.npc.test.api.interfaces.PathElementInterface;

import java.util.HashMap;
import java.util.Map;

public enum ProductApiValue implements PathElementInterface {
    NONE(""),
    LOOKUPCODE("getByLookUpCode");

    @Override
    public String getPathValue() {
        return value;
    }

    public static ProductApiValue map(String key) {
        if (valueMap == null) {
            valueMap = new HashMap<>();

            for (ProductApiValue value : ProductApiValue.values()) {
                valueMap.put(value.getPathValue(), value);
            }
        }

        return (valueMap.containsKey(key) ? valueMap.get(key) : ProductApiValue.NONE);
    }

    private String value;

    private static Map<String, ProductApiValue> valueMap = null;

    private ProductApiValue(String value) {
        this.value = value;
    }
}


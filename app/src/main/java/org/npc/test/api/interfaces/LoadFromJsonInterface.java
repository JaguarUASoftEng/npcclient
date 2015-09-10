package org.npc.test.api.interfaces;

import org.json.JSONObject;

public interface LoadFromJsonInterface<T> {
    T loadFromJson(JSONObject rawJsonObject);
}

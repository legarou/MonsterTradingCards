package at.technikum.Store;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StoreElementType {
    @JsonProperty(value = "FIRE")
    FIRE,
    @JsonProperty (value = "WATER")
    WATER,
    @JsonProperty (value = "NORMAL")
    NORMAL,
    @JsonProperty (value = "NONE")
    NONE
}

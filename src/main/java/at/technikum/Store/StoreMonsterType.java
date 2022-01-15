package at.technikum.Store;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StoreMonsterType {
    @JsonProperty(value = "MONSTER")
    MONSTER,
    @JsonProperty (value = "SPELL")
    SPELL,
    @JsonProperty (value = "NONE")
    NONE
}

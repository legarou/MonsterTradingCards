package at.technikum.Cards;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CardElementType {
    @JsonProperty (value = "FIRE")
    FIRE,
    @JsonProperty (value = "WATER")
    WATER,
    @JsonProperty (value = "NORMAL")
    NORMAL
}

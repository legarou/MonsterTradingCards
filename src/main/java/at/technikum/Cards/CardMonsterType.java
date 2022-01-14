package at.technikum.Cards;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CardMonsterType {
    @JsonProperty(value = "GOBLIN")
    GOBLIN,
    @JsonProperty (value = "DRAGON")
    DRAGON,
    @JsonProperty (value = "WIZZARD")
    WIZZARD,
    @JsonProperty (value = "ORK")
    ORK,
    @JsonProperty (value = "KNIGHT")
    KNIGHT,
    @JsonProperty (value = "KRAKEN")
    KRAKEN,
    @JsonProperty (value = "FIREELF")
    FIREELF,
    @JsonProperty (value = "SPELL")
    SPELL
}

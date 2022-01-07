package at.technikum.Server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty(value = "Username")
    private String username;
    @JsonProperty(value = "Password")
    private String password;
}

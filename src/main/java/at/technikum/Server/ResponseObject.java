package at.technikum.Server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;

public class ResponseObject {
    @JsonProperty(value = "Success")
     private String success;
    @JsonProperty(value = "Message")
     private String message;
    @JsonProperty(value = "Cause")
     private String cause;
    @JsonProperty(value = "Data")
     private HashMap data;
    @Getter
    @JsonIgnore
     private String status;

     public ResponseObject() {

     }

     public ResponseObject(String success, String message, String cause, HashMap data, int status) {
         this.success = success;
         this.message = message;
         this.cause = cause;
         this.data = data;
         setStatus(status);
     }

     public void setStatus(int stat) {
         switch (stat) {
             case 200 -> status = "200 OK";
             case 201 -> status = "201 Created";
             case 400 -> status = "400 Bad Request";
             case 401 -> status = "401 Unauthorized";
             case 402 -> status = "402 Payment Required";
             case 403 -> status = "403 Forbidden";
             case 404 -> status = "404 Not Found";
             case 405 -> status = "405 Method Not Allowed";
             case 406 -> status = "406 Not Acceptable";
             case 500 -> status = "500 Internal Server Error";
         }
     }
}

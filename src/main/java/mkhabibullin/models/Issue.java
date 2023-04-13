package mkhabibullin.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    @JsonProperty(value = "url")
    private String url;

    public String getUrl() {
        return url;
    }
}

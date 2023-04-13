package mkhabibullin.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    @JsonProperty("id")
    private String id;

    @JsonProperty("issue")
    private Issue issue;

    @JsonProperty("name")
    private String name;

    public String getDefectId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIssueCleanUrl() {
        return issue.getUrl().substring(1 + issue.getUrl().lastIndexOf("/"));
    }

}

package mkhabibullin.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefectResponse {
    @JsonProperty("content")
    private ArrayList<Content> content;

    public String getDefectId(int id) {
        return content.get(id).getDefectId();
    }

    public String getIssueCleanUrl(int id) {
        return content.get(id).getIssueCleanUrl();
    }

    public String getName(int id) {
        return content.get(id).getName();
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

}

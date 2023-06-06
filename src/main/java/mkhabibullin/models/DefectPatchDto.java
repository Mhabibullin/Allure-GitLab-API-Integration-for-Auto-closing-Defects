package mkhabibullin.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefectPatchDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("closed")
    private boolean closed;

    public void setName(String name) {
        this.name = name;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}

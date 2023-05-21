package mkhabibullin.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefectPatchDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("closed")
    private boolean closed;

    public DefectPatchDto(String name, boolean closed) {
        this.name = name;
        this.closed = closed;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}

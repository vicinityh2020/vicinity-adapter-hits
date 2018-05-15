package com.hits.vicinity.adapter.domain.vicinity;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pid",
        "monitors",
        "read_link",
        "write_link"
})
public class PropertySchema {

    @JsonProperty("pid")
    private String pid;
    @JsonProperty("monitors")
    private String monitors;
    @JsonProperty("read_link")
    private ReadLink readLink;
    @JsonProperty("write_link")
    private WriteLink writeLink;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pid")
    public String getPid() {
        return pid;
    }

    @JsonProperty("pid")
    public void setPid(String pid) {
        this.pid = pid;
    }

    @JsonProperty("monitors")
    public String getMonitors() {
        return monitors;
    }

    @JsonProperty("monitors")
    public void setMonitors(String monitors) {
        this.monitors = monitors;
    }

    @JsonProperty("read_link")
    public ReadLink getReadLink() {
        return readLink;
    }

    @JsonProperty("read_link")
    public void setReadLink(ReadLink readLink) {
        this.readLink = readLink;
    }

    @JsonProperty("write_link")
    public WriteLink getWriteLink() {
        return writeLink;
    }

    @JsonProperty("write_link")
    public void setWriteLink(WriteLink writeLink) {
        this.writeLink = writeLink;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
package com.hits.vicinity.adapter.domain.vicinity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "oid",
        "name",
        "type",
        "properties",
        "actions",
        "events"
})
public class ObjectsJson {

    @JsonProperty("oid")
    private String oid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("properties")
    private List<PropertySchema> properties = null;
    @JsonProperty("actions")
    private List<Object> actions = null;
    @JsonProperty("events")
    private List<JsonNode> events = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    @JsonProperty("oid")
    public void setOid(String oid) {
        this.oid = oid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("properties")
    public List<PropertySchema> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertySchema> properties) {
        this.properties = properties;
    }

    @JsonProperty("actions")
    public List<Object> getActions() {
        return actions;
    }

    @JsonProperty("actions")
    public void setActions(List<Object> actions) {
        this.actions = actions;
    }

    @JsonProperty("events")
    public List<JsonNode> getEvents() {
        return events;
    }

    @JsonProperty("events")
    public void setEvents(List<JsonNode> events) {
        this.events = events;
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
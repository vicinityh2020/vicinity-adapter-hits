package com.hits.vicinity.adapter.domain.vicinity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThingDescriptor {

    @JsonProperty("adapter-id")
    public String getAdapterId() {
        return adapterId;
    }

    @JsonProperty("adapter-id")
    public void setAdapterId(String adapterId) {
        this.adapterId = adapterId;
    }

    @JsonProperty("thing-descriptions")
    public List<ObjectsJson> getThingDescriptions() {
        return thingDescriptions;
    }

    @JsonProperty("thing-descriptions")
    public void setThingDescriptions(List<ObjectsJson> thingDescriptions) {
        this.thingDescriptions = thingDescriptions;
    }

    @JsonProperty("adapter-id")
    private String adapterId;

    @JsonProperty("thing-descriptions")
    private List<ObjectsJson> thingDescriptions;
}

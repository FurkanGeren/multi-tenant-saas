package org.saas.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "attribute_definitions")
public class AttributeDefinition extends Auditable {


    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String type; // TEXT, NUMBER, DATE, SELECT, etc.

    @Column(name = "options_json")
    private String optionsJson; // JSON string: ["red", "blue", "green"]

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptionsJson() {
        return optionsJson;
    }

    public void setOptionsJson(String optionsJson) {
        this.optionsJson = optionsJson;
    }
}
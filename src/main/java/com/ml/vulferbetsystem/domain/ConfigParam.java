package com.ml.vulferbetsystem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG_PARAM")
public class ConfigParam {

    @Id
    @SequenceGenerator(name = "config_param_generator", sequenceName = "CONFIG_PARAM_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "config_param_generator")
    private Long id;
    private String name;
    private String value;

    //getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.JsonDTO;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class UpdateRespDTO implements Serializable {
    Long id;
    String fieldName;
    String newValue;
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "RequestDTO{" +
                "id='" + id + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }

    public UpdateRespDTO() {
    }

    public UpdateRespDTO(Long id, String fieldName, String newValue) {
        this.id = id;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}


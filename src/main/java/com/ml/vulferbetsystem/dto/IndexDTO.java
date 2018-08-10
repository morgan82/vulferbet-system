package com.ml.vulferbetsystem.dto;

public class IndexDTO {

    private String[] URLs;

    public IndexDTO(String[] URLs) {
        this.URLs = URLs;
    }

    public IndexDTO() {
    }

    public String[] getURLs() {
        return URLs;
    }

    public void setURLs(String[] URLs) {
        this.URLs = URLs;
    }
}

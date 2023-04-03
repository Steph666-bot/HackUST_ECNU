package com.rfli.springbootrf.entity;

import lombok.Data;

/**
 * Doc Entity Class
 */
@Data
public class DocEntity {
    private Integer id;
    private String doc_identifier;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoc_identifier() {
        return doc_identifier;
    }

    public void setDoc_identifier(String doc_identifier) {
        this.doc_identifier = doc_identifier;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

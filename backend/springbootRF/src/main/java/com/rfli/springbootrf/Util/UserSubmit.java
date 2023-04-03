package com.rfli.springbootrf.Util;

public class UserSubmit {
    private String pk;
    private String nonce;
    private String doc_identifier;


    public String getDoc_identifier() {
        return doc_identifier;
    }

    public void setDoc_identifier(String doc_identifier) {
        this.doc_identifier = doc_identifier;
    }


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nounce) {
        this.nonce = nounce;
    }
}

package xyz.carlesllobet.pushmarket.Domain;

import android.net.Uri;

/**
 * Created by CarlesLlobet on 31/01/2016.
 */
public class Person {

    //private variables
    String userName;
    Integer punt;
    Uri foto;

    // Empty constructor
    public Person() {

    }

    // constructor
    public Person(String name, Integer punt, Uri pic) {
        this.userName = name;
        this.punt = punt;
        this.foto = pic;
    }

    // getting name
    public String getName() {
        return this.userName;
    }

    // setting name
    public void setName(String name) {
        this.userName = name;
    }

    //getting foto
    public Uri getFoto() {
        return this.foto;
    }

    //getting foto
    public void setFoto(Uri pic) {
        this.foto = pic;
    }

    // getting punctuation
    public Integer getPunt() {
        return this.punt;
    }

    // setting punctuation
    public void setPunt(Integer punt) {
        this.punt = punt;
    }
}

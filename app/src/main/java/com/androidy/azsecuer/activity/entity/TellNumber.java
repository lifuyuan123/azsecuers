package com.androidy.azsecuer.activity.entity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TellNumber {
    private String name,number;

    public TellNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TellNumber{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

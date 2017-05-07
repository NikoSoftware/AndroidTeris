package com.example.db;

import java.io.Serializable;

/**
 * Created by niko on 2017/5/7.
 */

public class UserInfo implements Serializable{
    private String name;
    private String passwords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }


}

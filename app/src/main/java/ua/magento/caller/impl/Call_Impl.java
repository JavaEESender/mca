package ua.magento.caller.impl;

import java.io.Serializable;

import ua.magento.caller.interfaces.Call;

/**
 * Created by Alexander on 18.09.2016.
 */
public class Call_Impl implements Call, Serializable {

    private String number;
    private long date;

    @Override
    public Call setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public Call setDate(long date) {
        this.date = date;
        return this;
    }

    @Override
    public long getDate() {
        return this.date;
    }

    @Override
    public String getNumber() {
        return this.number;
    }
}

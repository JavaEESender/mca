package ua.obolon.ponovoy.inerfaces;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface Call {

    Call setNumber(String number);
    Call setDate(long date);
    long getDate();
    String getNumber();

}
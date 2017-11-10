package ca.bcit.ass3.assignment3;

/**
 * Created by Lenic on 11/9/2017.
 */

public class PartyDetail {
    private String _name;
    private String _date;
    private String _time;
    private int _imageResourceId;

    public PartyDetail(String _name, String _date, String _time, int _imageResourceId) {
        this._name = _name;
        this._date = _date;
        this._time = _time;
        this._imageResourceId = _imageResourceId;
    }

    public String get_name() {
        return _name;
    }

    public String get_date() {
        return _date;
    }

    public String get_time() {
        return _time;
    }

    public int getImageResourceId() {
        return _imageResourceId;
    }

    @Override
    public String toString() {
        return "PartyDetail{" +
                "_name='" + _name + '\'' +
                ", _date='" + _date + '\'' +
                ", _time='" + _time + '\'' +
                '}';
    }
}

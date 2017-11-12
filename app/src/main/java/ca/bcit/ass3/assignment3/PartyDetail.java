package ca.bcit.ass3.assignment3;

/**
 * Created by Lenic on 11/9/2017.
 */

public class PartyDetail {
    private String _name;
    private String _date;
    private String _time;
    private int     _eventId;

    public PartyDetail(String _name, String _date, String _time) {
        this._name = _name;
        this._date = _date;
        this._time = _time;
    }

    public PartyDetail(String _name, String _date, String _time, int _eventId) {
        this._name = _name;
        this._date = _date;
        this._time = _time;
        this._eventId = _eventId;
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

    public int get_eventId() {
        return _eventId;
    }

    @Override
    public String toString() {
        return "Event: " + _name + "\n"
                + "Date: " + _date + "\n"
                + "Time: " + _time;
    }

}

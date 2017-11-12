package ca.bcit.ass3.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Lenic on 11/10/2017.
 */

public class ItemsDetails {
    private String  _name;
    private String  _unit;
    private int     quantity;
    private int     Event_ID;
    private int Item_ID;

    public ItemsDetails(String _name, String _unit, int quantity) {
        set_name(_name);
        set_unit(_unit);
        setQuantity(quantity);
    }

    public ItemsDetails(int item_ID, String _name, String _unit, int quantity, int event_ID) {
        set_name(_name);
        set_unit(_unit);
        setQuantity(quantity);
        this.Event_ID = event_ID;
        this.Item_ID = item_ID;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_unit() {
        return _unit;
    }

    public void set_unit(String _unit) {
        this._unit = _unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEventID() {
        return Event_ID;
    }

    public int getItem_ID() {
        return Item_ID;
    }

    @Override
    public String toString() {
        return "Item: " + _name + "\n"
                + "Unite: " + _unit + "\n"
                + "Quantity: " + quantity;
    }




}

package ca.bcit.ass3.assignment3;

/**
 * Created by Lenic on 11/10/2017.
 */

public class ItemsDetails {
    private String  _name;
    private String  _unit;
    private int     quantity;

    public ItemsDetails(String _name, String _unit, int quantity) {
        set_name(_name);
        set_unit(_unit);
        setQuantity(quantity);
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

    @Override
    public String toString() {
        return "Item: " + _name + "\n"
                + "Unite: " + _unit + "\n"
                + "Quantity: " + quantity;
    }
}

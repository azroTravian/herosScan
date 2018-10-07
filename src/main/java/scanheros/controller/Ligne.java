package scanheros.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ligne {

    private final SimpleStringProperty p;
    private final SimpleIntegerProperty i;

    public Ligne(String p, Integer i) {
        this.p = new SimpleStringProperty(p);
        this.i = new SimpleIntegerProperty(i);
    }

    @Override
    public String toString() {
        return "Ligne [p=" + p + ", i=" + i + "]";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Ligne other = (Ligne) obj;
        if (!(i.intValue() == other.i.intValue())) {
            return false;
        }
        return p.toString().equals(other.p.toString());
    }

    public String getPseudo() {
        return p.get();
    }

    public void setPseudo(String x) {
        p.set(x);
    }

    public Integer getId() {
        return i.get();
    }

    public void setId(Integer x) {
        i.set(x);
    }


}
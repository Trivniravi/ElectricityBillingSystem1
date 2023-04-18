package com.major.ebs.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name="Customer.fetchAll",
        query = "SELECT OBJECT(c) FROM Customer c")
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "units")
    private Double units = 0.0;

    @Transient
    public static final String COM = "Commercial", DOM = "Domestic", GOV="Government";

    public Customer (){}
    public Customer (String name, String type, String address){
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public boolean isComplete(){
        if(name==null || type==null || address==null)
            return false;
        return !name.isEmpty() && !type.isEmpty() && !address.isEmpty();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getUnits() {
        return units;
    }

    public void setUnits(Double reading) {
        this.units = units;
    }

    public void updateUnits(Double units){
        this.units+=units;
    }

    public double getRate(){
        double rate;
        switch (getType()){
            case Customer.COM: rate = 3.1; break;
            case Customer.GOV: rate = 2.0; break;
            default: rate = 2.3;
        }
        return rate;
    }

    @Override
    public String toString() {
        return "{ Name: "+name+", Address: "+address+", Type: "+type+" }\n";
    }
}

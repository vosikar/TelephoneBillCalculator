package com.phonecompany.billing;

public class PhoneRecord{

    private int calls;
    private double cost;

    public PhoneRecord(){
        this(0, 0);
    }

    public PhoneRecord(int calls, int cost){
        this.calls = calls;
        this.cost = cost;
    }

    public void addRecord(double cost){
        this.calls++;
        this.cost += cost;
    }

    public int getCalls(){
        return calls;
    }

    public double getCost(){
        return cost;
    }
}

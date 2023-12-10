package com.example.accident_statistics_calculator;

public class MainModel {

    String District;
    String State;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }



    MainModel()
    {

    }
    public MainModel(String district, String state) {
        District = district;
        State = state;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }
}

package com.example.themedicalc.dataholder;

public class InsuranceHolder {
    private String insurNum;
    private String name;
    private String discount;

    public InsuranceHolder(String insurNum, String name, String discount) {
        this.insurNum = insurNum;
        this.name = name;
        this.discount = discount;
    }

    public String getInsurNum() {
        return insurNum;
    }

    public String getName() {
        return name;
    }

    public Double getDiscount() {
        return Double.parseDouble(discount) / 100;
    }
}

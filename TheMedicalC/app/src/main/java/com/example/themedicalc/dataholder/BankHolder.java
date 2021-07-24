package com.example.themedicalc.dataholder;

public class BankHolder{
    private String accNum;
    private String bankName;
    private String fName;
    private String lName;
    private String money;

    public BankHolder(String accNum, String bankName, String fName, String lName, String money) {
        this.accNum = accNum;
        this.bankName = bankName;
        this.fName = fName;
        this.lName = lName;
        this.money = money;
    }

    public String getAccNum() {
        return accNum;
    }

    public String getBankOwner(){
        return fName + " " + lName;
    }

    public String getBankName() {
        return bankName;
    }

    public int getMoney() {
        return Integer.parseInt(money);
    }
}

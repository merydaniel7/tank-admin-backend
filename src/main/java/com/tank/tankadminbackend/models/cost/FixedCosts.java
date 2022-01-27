package com.tank.tankadminbackend.models.cost;

import javax.persistence.*;

@Entity
@Table(	name = "tankfixedcosts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "month")
        })
public class FixedCosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;

    private int wages;

    private int wageContribution;

    private int evContribution;

    private int packagingPrice;

    private int ahl;

    private int contabo;

    private int huszarBence;

    private int baranyArpad;

    private int accountant;

    private int audit;

    private int businessTax;

    private int corporateTax;

    private int corporateTax9;

    private int vat;

    private int constructionTax;

    private int overhead;

    private int other;

    private int unas;

    private int bankAccountCost;

    private int carInsurance;

    private int creditCardCommission;

    private int phoneBill;

    private int bonuses;

    private int plus1;

    private int plus2;

    private int plus3;

    private int plus4;

    private int plus5;

    private int plus6;

    private int plus7;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getWages() {
        return wages;
    }

    public void setWages(int wages) {
        this.wages = wages;
    }

    public int getWageContribution() {
        return wageContribution;
    }

    public void setWageContribution(int wageContribution) {
        this.wageContribution = wageContribution;
    }

    public int getEvContribution() {
        return evContribution;
    }

    public void setEvContribution(int evContribution) {
        this.evContribution = evContribution;
    }

    public int getPackagingPrice() {
        return packagingPrice;
    }

    public void setPackagingPrice(int packagingPrice) {
        this.packagingPrice = packagingPrice;
    }

    public int getAhl() {
        return ahl;
    }

    public void setAhl(int ahl) {
        this.ahl = ahl;
    }

    public int getContabo() {
        return contabo;
    }

    public void setContabo(int contabo) {
        this.contabo = contabo;
    }

    public int getHuszarBence() {
        return huszarBence;
    }

    public void setHuszarBence(int huszarBence) {
        this.huszarBence = huszarBence;
    }

    public int getBaranyArpad() {
        return baranyArpad;
    }

    public void setBaranyArpad(int baranyArpad) {
        this.baranyArpad = baranyArpad;
    }

    public int getAccountant() {
        return accountant;
    }

    public void setAccountant(int accountant) {
        this.accountant = accountant;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getBusinessTax() {
        return businessTax;
    }

    public void setBusinessTax(int businessTax) {
        this.businessTax = businessTax;
    }

    public int getCorporateTax() {
        return corporateTax;
    }

    public void setCorporateTax(int corporateTax) {
        this.corporateTax = corporateTax;
    }

    public int getCorporateTax9() {
        return corporateTax9;
    }

    public void setCorporateTax9(int corporateTax9) {
        this.corporateTax9 = corporateTax9;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getConstructionTax() {
        return constructionTax;
    }

    public void setConstructionTax(int constructionTax) {
        this.constructionTax = constructionTax;
    }

    public int getOverhead() {
        return overhead;
    }

    public void setOverhead(int overhead) {
        this.overhead = overhead;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getUnas() {
        return unas;
    }

    public void setUnas(int unas) {
        this.unas = unas;
    }

    public int getBankAccountCost() {
        return bankAccountCost;
    }

    public void setBankAccountCost(int bankAccountCost) {
        this.bankAccountCost = bankAccountCost;
    }

    public int getCarInsurance() {
        return carInsurance;
    }

    public void setCarInsurance(int carInsurance) {
        this.carInsurance = carInsurance;
    }

    public int getCreditCardCommission() {
        return creditCardCommission;
    }

    public void setCreditCardCommission(int creditCardCommission) {
        this.creditCardCommission = creditCardCommission;
    }

    public int getPhoneBill() {
        return phoneBill;
    }

    public void setPhoneBill(int phoneBill) {
        this.phoneBill = phoneBill;
    }


    public int getBonuses() {
        return bonuses;
    }

    public void setBonuses(int bonuses) {
        this.bonuses = bonuses;
    }

    public int getPlus1() {
        return plus1;
    }

    public void setPlus1(int plus1) {
        this.plus1 = plus1;
    }

    public int getPlus2() {
        return plus2;
    }

    public void setPlus2(int plus2) {
        this.plus2 = plus2;
    }

    public int getPlus3() {
        return plus3;
    }

    public void setPlus3(int plus3) {
        this.plus3 = plus3;
    }

    public int getPlus4() {
        return plus4;
    }

    public void setPlus4(int plus4) {
        this.plus4 = plus4;
    }

    public int getPlus5() {
        return plus5;
    }

    public void setPlus5(int plus5) {
        this.plus5 = plus5;
    }

    public int getPlus6() {
        return plus6;
    }

    public void setPlus6(int plus6) {
        this.plus6 = plus6;
    }

    public int getPlus7() {
        return plus7;
    }

    public void setPlus7(int plus7) {
        this.plus7 = plus7;
    }
}

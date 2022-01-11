package com.tank.tankadminbackend.models.lmdata;

import javax.persistence.*;

@Entity
@Table(	name = "lmmarketingcost",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "date")
        })
public class LmMarketingCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private Float facebook;

    private Float  google;

    private Float  argep;

    private Float  arukereso;

    private Float  sum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getFacebook() {
        return facebook;
    }

    public void setFacebook(Float facebook) {
        this.facebook = facebook;
    }

    public Float getGoogle() {
        return google;
    }

    public void setGoogle(Float google) {
        this.google = google;
    }

    public Float getArgep() {
        return argep;
    }

    public void setArgep(Float argep) {
        this.argep = argep;
    }

    public Float getArukereso() {
        return arukereso;
    }

    public void setArukereso(Float arukereso) {
        this.arukereso = arukereso;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }
}

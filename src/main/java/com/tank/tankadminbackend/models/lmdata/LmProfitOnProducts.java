package com.tank.tankadminbackend.models.lmdata;

import javax.persistence.*;

@Entity
@Table(	name = "lmprofitonproducts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "date")
        })
public class LmProfitOnProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private Float profit;

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

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }
}

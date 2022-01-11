package com.tank.tankadminbackend.models.lmdata;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private BigDecimal facebook;

    private BigDecimal  google;

    private BigDecimal  argep;

    private BigDecimal  arukereso;

    private BigDecimal  sum;


}

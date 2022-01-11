package com.tank.tankadminbackend.models.lmdata;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private BigDecimal profit;
}

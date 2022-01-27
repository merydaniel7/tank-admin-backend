package com.tank.tankadminbackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.api.ActualMonth;
import com.tank.tankadminbackend.models.cost.FixedCosts;
import com.tank.tankadminbackend.models.cost.FixedCostsList;
import com.tank.tankadminbackend.repository.FixedCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class FixedCostsService {
    @Autowired
    FixedCostRepository fixedCostRepository;

    public String getFixedCosts(String month) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ActualMonth actualMonth = mapper.readValue(month, ActualMonth.class);
        FixedCosts fixedCosts = fixedCostRepository.findByMonth(actualMonth.getMonth());
        if (fixedCosts != null) {
            return new ObjectMapper().writeValueAsString(fixedCosts);
        } else {
            return "Not found!";
        }
    }

    @Transactional
    public String saveFixedCost(String fixedCost) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FixedCostsList newFixedCostsList = mapper.readValue(fixedCost, FixedCostsList.class);
        FixedCosts newFixedCosts = newFixedCostsList.getFixedCosts();
        FixedCosts oldFixedCosts = fixedCostRepository.findByMonth(newFixedCosts.getMonth());
        if (oldFixedCosts != null) {
            oldFixedCosts.setWages(newFixedCosts.getWages());
            oldFixedCosts.setWageContribution(newFixedCosts.getWageContribution());
            oldFixedCosts.setEvContribution(newFixedCosts.getEvContribution());
            oldFixedCosts.setPackagingPrice(newFixedCosts.getPackagingPrice());
            oldFixedCosts.setAhl(newFixedCosts.getAhl());
            oldFixedCosts.setContabo(newFixedCosts.getContabo());
            oldFixedCosts.setHuszarBence(newFixedCosts.getHuszarBence());
            oldFixedCosts.setBaranyArpad(newFixedCosts.getBaranyArpad());
            oldFixedCosts.setAccountant(newFixedCosts.getAccountant());
            oldFixedCosts.setAudit(newFixedCosts.getAudit());
            oldFixedCosts.setBusinessTax(newFixedCosts.getBusinessTax());
            oldFixedCosts.setCorporateTax(newFixedCosts.getCorporateTax());
            oldFixedCosts.setCorporateTax9(newFixedCosts.getCorporateTax9());
            oldFixedCosts.setVat(newFixedCosts.getVat());
            oldFixedCosts.setConstructionTax(newFixedCosts.getConstructionTax());
            oldFixedCosts.setOverhead(newFixedCosts.getOverhead());
            oldFixedCosts.setOther(newFixedCosts.getOther());
            oldFixedCosts.setUnas(newFixedCosts.getUnas());
            oldFixedCosts.setBankAccountCost(newFixedCosts.getBankAccountCost());
            oldFixedCosts.setCarInsurance(newFixedCosts.getCarInsurance());
            oldFixedCosts.setCreditCardCommission(newFixedCosts.getCreditCardCommission());
            oldFixedCosts.setPhoneBill(newFixedCosts.getPhoneBill());
            oldFixedCosts.setBonuses(newFixedCosts.getBonuses());
            oldFixedCosts.setPlus1(newFixedCosts.getPlus1());
            oldFixedCosts.setPlus2(newFixedCosts.getPlus2());
            oldFixedCosts.setPlus3(newFixedCosts.getPlus3());
            oldFixedCosts.setPlus4(newFixedCosts.getPlus4());
            oldFixedCosts.setPlus5(newFixedCosts.getPlus5());
            oldFixedCosts.setPlus6(newFixedCosts.getPlus6());
            oldFixedCosts.setPlus7(newFixedCosts.getPlus7());
            fixedCostRepository.save(oldFixedCosts);
            return "Saved to database!";
        } else {
            return "Not found!";
        }
    }

}

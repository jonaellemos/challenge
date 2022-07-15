package com.ilegra.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Vendas {

    private String id;
    private Integer quantidade;
    private String precoItem;

    @Override
    public String toString() {
        return "Vendas{" +
                "id='" + id + '\'' +
                ", quantidade=" + quantidade +
                ", precoItem='" + precoItem + '\'' +
                '}';
    }
}

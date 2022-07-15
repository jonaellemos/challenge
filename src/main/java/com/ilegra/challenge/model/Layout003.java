package com.ilegra.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Layout003 {

    //003çID da vendaç[ID do item-Quantidade do item-Preço do item]çNome do vendedor

    private String id;
    private String idVenda;
    private List<Vendas> vendas;
    private String vendedor;
//    private List<Vendas> vendas;
//    private Layout001 vendedor;


    @Override
    public String toString() {
        return "Layout003{" +
                "id='" + id + '\'' +
                ", idVenda='" + idVenda + '\'' +
                ", vendas=" + vendas +
                ", vendedor='" + vendedor + '\'' +
                '}';
    }
}

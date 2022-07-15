package com.ilegra.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Layout001 {

    private String id;
    private String nome;
    private String cpf;
    private String salario;

//    public Layout001(Long id, String nome,String cpf, BigDecimal salario) {
//        this.id = id;
//        this.nome = nome;
//        this.cpf = cpf;
//        this.salario = salario;
//    }

    @Override
    public String toString() {
        return "Layout001{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                '}';
    }
}

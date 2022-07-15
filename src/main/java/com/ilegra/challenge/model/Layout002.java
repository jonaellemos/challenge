package com.ilegra.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Layout002 {

    private String id;
    private String cnpj;
    private String nome;
    private String areaNegocio;


    @Override
    public String toString() {
        return "Layout002{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", areaNegocio='" + areaNegocio + '\'' +
                '}';
    }
}

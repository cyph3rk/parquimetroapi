package com.parquimetroapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parquimetroapi.controller.form.ValoresForm;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ValoresDto {

    private Long id;
    private String veiculo;
    private BigDecimal valor;
    private BigDecimal fracao;

    public ValoresDto() {
    }

    public ValoresDto(String veiculo, BigDecimal valor, BigDecimal fracao) {
        this.veiculo = veiculo;
        this.valor = valor;
        this.fracao = fracao;
    }

    public ValoresDto(ValoresForm valoresForm) {
        this.veiculo = valoresForm.getVeiculo();
        this.valor = valoresForm.getValor();
        this.fracao = valoresForm.getFracao();
    }
}

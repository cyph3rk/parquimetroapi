package com.parquimetroapi.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parquimetroapi.dominio.Valores;
import com.parquimetroapi.dto.ValoresDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ValoresForm {

    @JsonProperty
    @NotBlank(message = "Campo veiculo é obrigatório")
    private String veiculo;

    @JsonProperty
    @NotNull(message = "Campo valor é obrigatório")
    private BigDecimal valor;

    @JsonProperty
    @NotNull(message = "Campo fracao é obrigatório")
    private BigDecimal fracao;

    public ValoresDto toValoresDto() {
        return new ValoresDto(veiculo, valor, fracao);
    }

}

package com.parquimetroapi.controller.form;

import com.parquimetroapi.dto.TicketDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TicketForm {

    @JsonProperty
    @NotBlank(message = "Código da TAG é obrigatório")
    private String codigotag;

    @JsonProperty
    private LocalDateTime entrada;

    @JsonProperty
    private LocalDateTime saida;

    @JsonProperty
    private BigDecimal valor;

    public TicketDto toTicketDto() {
        return new TicketDto(codigotag, entrada, saida, valor);
    }

}

package com.parquimetroapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TicketDto {

    private Long id;
    private String codigotag;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private BigDecimal valor;

    private BigDecimal conta;

    public TicketDto() {

    }

    public TicketDto(String codigotag, LocalDateTime saida) {
        this.codigotag = codigotag;
        this.saida = saida;
    }

    public TicketDto(String codigotag, LocalDateTime entrada, BigDecimal valor) {
        this.codigotag = codigotag;
        this.entrada = entrada;
        this.valor = valor;
    }

    public TicketDto(String codigotag, LocalDateTime entrada, LocalDateTime saida, BigDecimal valor) {
        this.codigotag = codigotag;
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
    }
}

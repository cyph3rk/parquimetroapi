package com.parquimetroapi.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe de negocio ticket com os dados relevantes ao ticket.
 *
 *
 * @author Diego Vargas
 */

@Setter
@Getter
@Entity
@Table(name = "ticket")
public class Ticket {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String codigotag;

    @JsonProperty
    private LocalDateTime entrada;

    @JsonProperty
    private LocalDateTime saida;

    @JsonProperty
    private BigDecimal valor;

    public Ticket(String codigotag, LocalDateTime entrada, LocalDateTime saida, BigDecimal valor) {
        this.codigotag = codigotag;
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
    }

    public Ticket() {

    }
}

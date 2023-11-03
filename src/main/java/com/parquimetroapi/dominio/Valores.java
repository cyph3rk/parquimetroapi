package com.parquimetroapi.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "valores")
public class Valores {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String veiculo;

    @JsonProperty
    private BigDecimal valor;

    @JsonProperty
    private BigDecimal fracao;

    public Valores(String veiculo, BigDecimal valor, BigDecimal fracao) {
        this.veiculo = veiculo;
        this.valor = valor;
        this.fracao = fracao;
    }

    public Valores() {
    }
}

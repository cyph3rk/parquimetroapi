package com.parquimetroapi.facade;

import com.parquimetroapi.dominio.Ticket;
import com.parquimetroapi.dominio.Valores;
import com.parquimetroapi.dto.TicketDto;
import com.parquimetroapi.dto.ValoresDto;
import com.parquimetroapi.repositorio.IValoresRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValoresFacade {

    private static final Logger logger = LoggerFactory.getLogger(TicketFacade.class);

    private final IValoresRepositorio valoresRepositorio;

    public ValoresFacade(IValoresRepositorio valoresRepositorio) {
        this.valoresRepositorio = valoresRepositorio;
    }

    private ValoresDto converter (Valores valores) {
        ValoresDto valoresDto = new ValoresDto();
        valoresDto.setId(valores.getId());
        valoresDto.setVeiculo(valores.getVeiculo());
        valoresDto.setValor(valores.getValor());
        valoresDto.setFracao(valores.getFracao());

        return valoresDto;
    }

    public List<ValoresDto> buscarPorVeiculo(String veiculo) {
        List<Valores> valoresList = valoresRepositorio.findByVeiculo(veiculo);

        return valoresList.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Long salvaValores(ValoresDto valoresDto) {

        Valores valores = new Valores();

        List<ValoresDto> encontrado = buscarPorVeiculo(valoresDto.getVeiculo());
        if (encontrado.size() >= 1) {
            ValoresDto valoresDtoEncontrado = encontrado.stream().findFirst().orElse(null);
            valores.setId(valoresDtoEncontrado.getId());
        }

        valores.setVeiculo(valoresDto.getVeiculo());
        valores.setValor(valoresDto.getValor());
        valores.setFracao(valoresDto.getFracao());

        valoresRepositorio.save(valores);

        return valores.getId();
    }


    public List<ValoresDto> listaTodosValores() {
        return valoresRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}

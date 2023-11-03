package com.parquimetroapi.facade;

import com.parquimetroapi.dominio.Ticket;
import com.parquimetroapi.dto.TicketDto;
import com.parquimetroapi.repositorio.ITicketRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketFacade {

    private static final Logger logger = LoggerFactory.getLogger(TicketFacade.class);

    private final ITicketRepositorio ticketRepositorio;

    @Autowired
    public TicketFacade(ITicketRepositorio ticketRepositorio) {
        this.ticketRepositorio = ticketRepositorio;
    }

    private String saida_liberada(Ticket ticket) {

        logger.info("TicketFacade - SAIDA LIBERADA tag: " + ticket.getCodigotag());

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setCodigotag(ticket.getCodigotag());
        ticketDto.setEntrada(ticket.getEntrada());
        ticketDto.setSaida(ticket.getSaida());
        ticketDto.setValor(ticket.getValor());

        logger.info("TicketFacade - SAIDA LIBERADA tag: " + ticketDto.getCodigotag());

        if (ticketDto.getValor() == null || ticketDto.getSaida() == null) {
            String result = "{\"Erro\": \"Ticker não foi pago\"}";
            return result;
        }

        Duration duracao = Duration.between(ticket.getSaida(), LocalDateTime.now());
        long minutosDecorridos = duracao.toMinutes();

        if (minutosDecorridos <= 30) {
            String result = "{\"msg\": \"Ticker liberado para saida\"}";
            return result;
        } else {
            String result = "{\"Erro\": \"Tolerancia de permanencia extrapolado\"}";
            return result;
        }
    }

    private TicketDto converter (Ticket ticket) {
        TicketDto result = new TicketDto();
        result.setId(ticket.getId());
        result.setCodigotag(ticket.getCodigotag());
        result.setEntrada(ticket.getEntrada());
        result.setSaida(ticket.getSaida());
        result.setValor(ticket.getValor());

        if (result.getSaida() != null) {
            logger.info("TicketFacade - SAIDA tag: " + result.getCodigotag());

            Duration duracao = Duration.between(ticket.getEntrada(), ticket.getSaida());
            long minutosDecorridos = duracao.toMinutes();

            if (minutosDecorridos <= 240) {
                result.setConta(ticket.getValor());
            } else {
                result.setConta(ticket.getValor().multiply(new BigDecimal(2)));
            }
        }

        return result;
    }

    public Long salvarEntrada(TicketDto ticketDto) {

        Ticket ticket = new Ticket();
        ticket.setCodigotag(ticketDto.getCodigotag());
        ticket.setEntrada(ticketDto.getEntrada());
        ticket.setValor(ticketDto.getValor());

        ticketRepositorio.save(ticket);

        return ticket.getId();
    }

    public BigDecimal salvarSaida(TicketDto ticketDto) {

        List<TicketDto> encontrado = buscarTicketPorTag(ticketDto.getCodigotag());
        if (encontrado.size() <= 0) {
            return BigDecimal.ZERO;
        }

        Ticket ticket = new Ticket();
        ticket.setId(encontrado.get(0).getId());
        ticket.setCodigotag(ticketDto.getCodigotag());
        ticket.setEntrada(encontrado.get(0).getEntrada());
        ticket.setSaida(ticketDto.getSaida());
        ticket.setValor(encontrado.get(0).getValor());

        ticketRepositorio.save(ticket);

        return new BigDecimal(10);
    }

    public List<TicketDto> buscarTicketPorTag(String codigotag) {
        List<Ticket> ticketList = ticketRepositorio.findByCodigotag(codigotag);

        return ticketList.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public String getSaidaLiberada(String codigotag) {

        logger.info("TicketFacade - SAIDA LIBERADA tag: " + codigotag);

        List<Ticket> ticketList = ticketRepositorio.findByCodigotag(codigotag);

        Ticket ticket = ticketList.stream().findFirst().orElse(null);
        if (ticket == null) {
            return "{\"Erro\": \"Tag NÃO cadastrada erro:1231.\"}";
        }

        String result = saida_liberada(ticket);
        if (result == null) {
            result = "{\"Erro\": \"Tag NÃO cadastrada erro:1232.\"}";
        }
        return result;
    }


    public List<TicketDto> getListaEmAberto() {
        List<Ticket> ticketList = ticketRepositorio.buscaListaTickesEmAberto();

        return ticketList.stream()
                .map(this::converter).collect(Collectors.toList());
    }
}

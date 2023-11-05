package com.parquimetroapi.controller;

import com.parquimetroapi.controller.form.TicketForm;
import com.parquimetroapi.dto.TicketDto;
import com.parquimetroapi.facade.TicketFacade;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller Ticket de interações as funcionalidades relacionada a manutenção de ticket e tags
 *
 *
 * @author Diego Vargas
 */

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final Validator validator;

    private final TicketFacade ticketFacade;

    @Autowired
    public TicketController(Validator validator, TicketFacade ticketFacade) {
        this.validator = validator;
        this.ticketFacade = ticketFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    /**
     * Função para registro de entrada de uma tag
     * @param ticketForm json com os parametros de entrada da Tag
     * @return Mensagem conformando sucesso ou não no cadastro da Tag
     *
     * @author Diego Vargas
     */
    @PostMapping("/entrada")
    public ResponseEntity<Object> ticketEntrada(@RequestBody TicketForm ticketForm) {
        logger.info("POST - Registra Ticket Entrada");

        Map<Path, String> violacoesToMap = validar(ticketForm);
        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        if (ticketForm.getValor() == null) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Campo Valor é obrigatório\"}");
        }

        //TODO: Implementar bloqueio para não permitir cadastro de codigo tags repetidas
        //      Mas se o campo saida estiver preenchido pode cadastrar.

        TicketDto ticketDto = new TicketDto(ticketForm.getCodigotag(), LocalDateTime.now(), ticketForm.getValor());
        Long resp = ticketFacade.salvarEntrada(ticketDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Não foi possível registrar entrada\"}");
        }

        logger.info("POST - Registro ticket entrada com sucesso: " + ticketForm.getCodigotag());
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Entrada registrada com sucesso.\"}");
    }

    /**
     * Função para registra a saida de uma tag
     * @param ticketForm json com o número da Tag
     * @return Mensagem com valor a ser cobrado.
     *
     * @author Diego Vargas
     */
    @PostMapping("/saida")
    public ResponseEntity<Object> ticketSaida(@RequestBody TicketForm ticketForm) {
        logger.info("POST - Registra Ticket Saida");

        Map<Path, String> violacoesToMap = validar(ticketForm);
        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        TicketDto ticketDto = new TicketDto(ticketForm.getCodigotag(), LocalDateTime.now());
        BigDecimal resp = ticketFacade.salvarSaida(ticketDto);
        if ( resp.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Não foi possível registrar saida\"}");
        }

        logger.info("POST - Registro ticket saida com sucesso: " + ticketForm.getCodigotag());
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"saida registrada com sucesso.\"}");
    }

    /**
     * Função para consultar os dados registrados de uma tag pelo seu número
     * @param codigotag código a tag a ser consultada
     * @return retorna json com os dados regitrados tag informada.
     *
     * @author Diego Vargas
     */
    @GetMapping("/{codigotag}")
    public ResponseEntity<Object> getTicketPorTag(@PathVariable String codigotag) {
        logger.info("GET - Pega ticket por tag: " + codigotag);

        List<TicketDto> ticketDto = ticketFacade.buscarTicketPorTag(codigotag);

        if (ticketDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Tag NÃO cadastrada.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }


    /**
     * Funçaõ que retorna se uma tag está liberada para sair.
     * @param codigotag código a tag a ser consultada
     * @return mensagem informando se a Tag pode ou não sair
     *
     * @author Diego Vargas
     */
    @GetMapping("/saidaliberada/{codigotag}")
    public ResponseEntity<Object> getSaidaLiberada(@PathVariable String codigotag) {
        logger.info("GET - Saida Liberada - por tag: " + codigotag);

        String saida =  ticketFacade.getSaidaLiberada(codigotag);

        return ResponseEntity.status(HttpStatus.OK).body(saida);
    }

    /**
     * Lista dodas as tag com valor em aberto.
     * @return json com os dados de todas as tags em aberto
     *
     * @author Diego Vargas
     */
    @GetMapping("/listaemaberto")
    public ResponseEntity<Object> getListaEmAberto() {
        logger.info("GET - Retorna lista de tags em aberto");

        List<TicketDto> ticketDtoList =  ticketFacade.getListaEmAberto();

        return ResponseEntity.status(HttpStatus.OK).body(ticketDtoList);
    }


}

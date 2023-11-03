package com.parquimetroapi.controller;

import com.parquimetroapi.controller.TicketController;
import com.parquimetroapi.controller.form.TicketForm;
import com.parquimetroapi.controller.form.ValoresForm;
import com.parquimetroapi.dominio.Valores;
import com.parquimetroapi.dto.TicketDto;
import com.parquimetroapi.dto.ValoresDto;
import com.parquimetroapi.facade.TicketFacade;
import com.parquimetroapi.facade.ValoresFacade;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/valores")
public class ValoresController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final Validator validator;

    private final ValoresFacade valoresFacade;

    @Autowired
    public ValoresController(Validator validator, ValoresFacade valoresFacade) {
        this.validator = validator;
        this.valoresFacade = valoresFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Object> ticketEntrada(@RequestBody ValoresForm valoresForm) {
        logger.info("POST - Registra veiculo e valor");

        Map<Path, String> violacoesToMap = validar(valoresForm);
        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        ValoresDto valoresDto = new ValoresDto(valoresForm);
        Long resp = valoresFacade.salvaValores(valoresDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Não foi possível registrar veiculo\"}");
        }
        valoresDto.setId(resp);

        return ResponseEntity.status(HttpStatus.CREATED).body(valoresDto);
    }

    @GetMapping("/lista")
    public ResponseEntity<Object> getTodosValores() {
        logger.info("GET - Lista todos os valores");

        List<ValoresDto> valoresDtos = valoresFacade.listaTodosValores();

        if (valoresDtos.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Não foi possível gerar a lista de todos.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(valoresDtos);
    }

}

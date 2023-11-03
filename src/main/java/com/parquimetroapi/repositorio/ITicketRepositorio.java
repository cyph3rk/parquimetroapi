package com.parquimetroapi.repositorio;

import com.parquimetroapi.dominio.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITicketRepositorio  extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCodigotag(String codigotag);

    @Query("from Ticket t where t.saida = null")
    List<Ticket> buscaListaTickesEmAberto();

}

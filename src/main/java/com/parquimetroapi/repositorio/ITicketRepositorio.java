package com.parquimetroapi.repositorio;

import com.parquimetroapi.dominio.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepositorio  extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCodigotag(String codigotag);

    @Query("from Ticket t where t.saida = null")
    List<Ticket> buscaListaTickesEmAberto();

}

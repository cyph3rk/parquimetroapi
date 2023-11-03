package com.parquimetroapi.repositorio;

import com.parquimetroapi.dominio.Ticket;
import com.parquimetroapi.dominio.Valores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IValoresRepositorio extends JpaRepository<Valores, Long> {

    List<Valores> findByVeiculo(String veiculo);
}

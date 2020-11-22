package br.com.zup.estrelas.sistema.prefeitura.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.enums.Area;

@Repository
public interface SecretariaRepository extends CrudRepository<Secretaria, Long> {
	
    // TODO: Boa!
	boolean existsByArea(Area area);
	
//	Secretaria findByArea(Area area);
	
}

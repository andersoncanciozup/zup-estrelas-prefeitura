package br.com.zup.estrelas.sistema.prefeitura.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.sistema.prefeitura.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
	
	boolean existsByCpf(String cpf);
	
	//FIXME: Remover código comentado
//	Funcionario findByCpf(String cpf);
	
}

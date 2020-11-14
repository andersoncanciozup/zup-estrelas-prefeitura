package br.com.zup.estrelas.sistema.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoConcluidoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Projeto;

public interface IProjetoService {
	
	MensagemDTO criaProjeto(ProjetoDTO projeto);
	
	Projeto consultaProjeto(Long idProjeto);
	
	MensagemDTO alteraProjeto(Long idProjeto, AlteraProjetoDTO alteracao);
	
	List<Projeto> listaProjetos();

	MensagemDTO projetoConcluido(Long idProjeto, ProjetoConcluidoDTO dataTermino);
}

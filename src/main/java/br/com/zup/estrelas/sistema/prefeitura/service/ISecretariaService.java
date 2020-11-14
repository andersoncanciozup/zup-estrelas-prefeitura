package br.com.zup.estrelas.sistema.prefeitura.service;

import br.com.zup.estrelas.sistema.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;

import java.util.List;

import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;


public interface ISecretariaService {

	public MensagemDTO criaSecretaria(SecretariaDTO cadastroSecretariaDTO);

	public Secretaria consultaSecretaria(Long idSecretaria);
	
	public MensagemDTO alteraSecretaria(Long idSecretaria, SecretariaDTO alteracaoSecretariaDTO);
	
	public MensagemDTO removeSecretaria(Long idSecretaria);
	
	public List<Secretaria> listaSecretaria();
	
}

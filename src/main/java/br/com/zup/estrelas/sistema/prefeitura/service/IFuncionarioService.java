package br.com.zup.estrelas.sistema.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraFuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Funcionario;

public interface IFuncionarioService {

	public MensagemDTO criaFuncionario(FuncionarioDTO funcionario);
	
	public Funcionario consultaFuncionario(Long idFuncionario);
	
	public MensagemDTO alteraFuncionario(Long idFuncionario, AlteraFuncionarioDTO alteracaoFuncionario);
	
	public MensagemDTO removeFuncionario(Long idFuncionario);
	
	public List<Funcionario> listaFuncionarios();
	
}

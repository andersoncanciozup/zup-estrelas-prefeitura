package br.com.zup.estrelas.sistema.prefeitura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraFuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Funcionario;
import br.com.zup.estrelas.sistema.prefeitura.service.IFuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
		
	@Autowired
	IFuncionarioService funcionarioService;
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO criaFuncionario(@RequestBody FuncionarioDTO funcionario) {	
		return funcionarioService.criaFuncionario(funcionario);
	}
	
	@GetMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public Funcionario consultaFuncionario(@PathVariable Long idFuncionario) {
		return funcionarioService.consultaFuncionario(idFuncionario);
	}
	
	@PutMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public MensagemDTO alteraFuncionario(@PathVariable Long idFuncionario, @RequestBody AlteraFuncionarioDTO alteracaoFuncionario) {
		return funcionarioService.alteraFuncionario(idFuncionario, alteracaoFuncionario);
	}
	
	@DeleteMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public MensagemDTO removeFuncionario(@PathVariable Long idFuncionario) {
		return funcionarioService.removeFuncionario(idFuncionario);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Funcionario> listaFuncionarios() {
		return funcionarioService.listaFuncionarios();
	}
}

package br.com.zup.estrelas.sistema.prefeitura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoConcluidoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Projeto;
import br.com.zup.estrelas.sistema.prefeitura.service.IProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	IProjetoService projetoService;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO criaProjeto(@RequestBody ProjetoDTO projeto) {
		return projetoService.criaProjeto(projeto);
	}

	@GetMapping(path = "/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Projeto consultaProjeto(@PathVariable Long idProjeto) {
		return projetoService.consultaProjeto(idProjeto);
	}

	@PutMapping(path = "/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alteraProjeto(@PathVariable Long idProjeto, @RequestBody AlteraProjetoDTO alteracaoProjeto) {
		return projetoService.alteraProjeto(idProjeto, alteracaoProjeto);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Projeto> listaProjetos() {
		return projetoService.listaProjetos();
	}

	@PutMapping(path = "/concluido/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO projetoConcluido(@PathVariable Long idProjeto, @RequestBody ProjetoConcluidoDTO dataTermino) {
		return projetoService.projetoConcluido(idProjeto, dataTermino);
	}
}

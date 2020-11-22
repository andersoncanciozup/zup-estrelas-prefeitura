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

import br.com.zup.estrelas.sistema.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.service.ISecretariaService;

@RestController
@RequestMapping("/secretarias")
public class SecretariaController {
	
	@Autowired
	ISecretariaService secretariaService;
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO criaSecretaria(@RequestBody SecretariaDTO cadastroSecretariaDTO) {
	
		return secretariaService.criaSecretaria(cadastroSecretariaDTO);	
	}
	
	 @GetMapping(path = "/{idSecretaria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	 public Secretaria consultaSecretaria(@PathVariable Long idSecretaria) {
		 return secretariaService.consultaSecretaria(idSecretaria);
	 }

	 // FIXME: Aqui a variável não é a area, isso faz com que o put não funcione
	 // pois está mapeada na anotação area e na assinatura do método o idSecretaria.
	 // Essa correspondência não é obrigatória, apesar de tornar as coisas mais fáceis
	 // mas se vc necessitar de usar nomes diferentes tem a propriedade "name" da anotação
	 // PathVariable.
	 @PutMapping(path = "/{area}", produces = { MediaType.APPLICATION_JSON_VALUE })
	 public MensagemDTO alteraSecretaria(@PathVariable Long idSecretaria, @RequestBody SecretariaDTO alteracaoSecretariaDTO) {
		 
		 return secretariaService.alteraSecretaria(idSecretaria, alteracaoSecretariaDTO);
	 }
	 
	 @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	 public List<Secretaria> listaSecretarias() {
		 return secretariaService.listaSecretaria();
	 }
	 
	 @DeleteMapping(path = "/{idSecretaria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	 public MensagemDTO removeSecretaria(@PathVariable Long idSecretaria) {
		 return secretariaService.removeSecretaria(idSecretaria);
	 }
	 
}

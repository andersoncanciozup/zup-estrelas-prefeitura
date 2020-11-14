package br.com.zup.estrelas.sistema.prefeitura.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.enums.Area;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@RunWith(MockitoJUnitRunner.class)
public class SecretarariaServiceTests {

	private static final MensagemDTO SECRETARIA_ALTERADA_COM_SUCESSO = new MensagemDTO("Secretaria alterada com sucesso");

	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não encontrada");

	private static final MensagemDTO SECRETARIA_REMOVIDA_COM_SUCESSO = new MensagemDTO("Secretaria removida com sucesso");

	private static final MensagemDTO SECRETARIA_EXISTENTE = new MensagemDTO("Secretaria já existente");

	private static final MensagemDTO SECRETARIA_CRIADA_COM_SUCESSO = new MensagemDTO("Secretaria cadastrada com sucesso");

	@Mock
	SecretariaRepository secretariaRepository;

	@InjectMocks
	SecretariaService secretariaService;
	
	private SecretariaDTO InstanciaSecretariaDTO() {
		SecretariaDTO secretaria = new SecretariaDTO();
		secretaria.setArea(Area.SAUDE);
		secretaria.setEmail("saude@am.com.br");
		secretaria.setEndereco("Av. Oscar Borel");
		secretaria.setOrcamentoFolha(20.000);
		secretaria.setOrcamentoProjetos(100000.00);
		secretaria.setSite("www.saudeam.com.br");
		secretaria.setTelefone("(092) 99999-0000 ");
		return secretaria;
	}
	
	@Test
	public void deveCriarUmaSecretariaComSucesso() {

		SecretariaDTO secretaria = InstanciaSecretariaDTO();

		Mockito.when(secretariaRepository.existsByArea(Area.SAUDE)).thenReturn(false);

		MensagemDTO mensagemRetornada = this.secretariaService.criaSecretaria(secretaria);
		MensagemDTO mensagemEsperada = SECRETARIA_CRIADA_COM_SUCESSO;

		Assert.assertEquals("Deve criar uma secretaria com sucesso", mensagemEsperada, mensagemRetornada);

	}

	@Test
	public void naoDeveCriarSecretariaExistente() {

		SecretariaDTO secretaria = InstanciaSecretariaDTO();

		Mockito.when(secretariaRepository.existsByArea(Area.SAUDE)).thenReturn(true);

		MensagemDTO mensagemRetornada = this.secretariaService.criaSecretaria(secretaria);
		MensagemDTO mensagemEsperada = SECRETARIA_EXISTENTE;

		Assert.assertEquals("Não deve criar uma secretaria caso ja exista", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void deveRemoverUmaSecretariaComSucesso() {
		
		Mockito.when(secretariaRepository.existsById(12L)).thenReturn(true);

		MensagemDTO mensagemRetornada = this.secretariaService.removeSecretaria(12L);
		MensagemDTO mensagemEsperada = SECRETARIA_REMOVIDA_COM_SUCESSO;

		Assert.assertEquals(mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveRemoverUmaSecretariaInexistente() {
		
		Mockito.when(secretariaRepository.existsById(12L)).thenReturn(false);
		
		MensagemDTO mensagemRetornada = this.secretariaService.removeSecretaria(12L);
		MensagemDTO mensagemEsperada = SECRETARIA_INEXISTENTE;
		
		Assert.assertEquals(mensagemEsperada, mensagemRetornada);	
	}
	
	@Test
	public void deveAlterarUmaSecretariaComSucesso() {
		
		Secretaria secretaria = new Secretaria();
		secretaria.setArea(Area.SAUDE);
		secretaria.setEmail("saude@am.com.br");
		secretaria.setEndereco("Av. Oscar Borel");
		secretaria.setOrcamentoFolha(20.000);
		secretaria.setOrcamentoProjetos(100000.00);
		secretaria.setSite("www.saudeam.com.br");
		secretaria.setTelefone("(092) 99999-0000 ");
		
		Optional<Secretaria> secretariaBD = Optional.of(secretaria);
		
		Mockito.when(secretariaRepository.findById(11L)).thenReturn(secretariaBD);
		
	
		SecretariaDTO alteracaoSecretariaDTO = InstanciaSecretariaDTO();
		
		MensagemDTO mensagemRetornada = this.secretariaService.alteraSecretaria(11L, alteracaoSecretariaDTO);
		MensagemDTO mensagemEsperada = SECRETARIA_ALTERADA_COM_SUCESSO;
		
		Assert.assertEquals("Deve alterar uma secretaria com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveAlterarUmaSecretariaInexistente() {
		
		Optional<Secretaria> secretariaBD = Optional.empty();
		
		Mockito.when(secretariaRepository.findById(11L)).thenReturn(secretariaBD);
		
	
		SecretariaDTO alteracaoSecretariaDTO = InstanciaSecretariaDTO();
		
		MensagemDTO mensagemRetornada = this.secretariaService.alteraSecretaria(11L, alteracaoSecretariaDTO);
		MensagemDTO mensagemEsperada = SECRETARIA_INEXISTENTE;;
		
		Assert.assertEquals("Não deve alterar uma secretaria inexistente", mensagemEsperada, mensagemRetornada);
	}

}

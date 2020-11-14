package br.com.zup.estrelas.sistema.prefeitura.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoConcluidoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Projeto;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.enums.Area;
import br.com.zup.estrelas.sistema.prefeitura.repository.ProjetoRepository;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceTestes {

	private static final MensagemDTO PROJETO_CONCLUIDO = new MensagemDTO("Projeto concluido com sucesso");

	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não existente");

	private static final MensagemDTO PROJETO_EXISTENTE = new MensagemDTO("Projeto com o mesmo nome já existente");

	private static final MensagemDTO PROJETO_CRIADO_SUCESSO = new MensagemDTO("Projeto criado com sucesso");

	private static final MensagemDTO DATA_DE_INICIO_MAIOR_QUE_ENTREGA = new MensagemDTO("A data da entrega não pode ser maior do que início");
	
	private static final MensagemDTO PROJETO_INEXISTENTE = new MensagemDTO("Projeto não encontrado");
	
	@Mock
	ProjetoRepository projetoRepository;

	@Mock
	SecretariaRepository secretariaRepository;

	@InjectMocks
	ProjetoService projetoService;

	@Test
	public void deveCriarProjetoComSucesso() {
		ProjetoDTO projeto = instanciaProjeto();

		Optional<Secretaria> secretariaBD = instanciaOptionalSecretaria();

		Mockito.when(projetoRepository.existsByNome(projeto.getNome())).thenReturn(false);

		Mockito.when(secretariaRepository.findById(projeto.getIdSecretaria())).thenReturn(secretariaBD);

		MensagemDTO mensagemRecebida = projetoService.criaProjeto(projeto);
		MensagemDTO mensagemEsperada = PROJETO_CRIADO_SUCESSO;

		Assert.assertEquals("Deve criar projeto com sucesso", mensagemEsperada, mensagemRecebida);

	}

	@Test
	public void naoDeveCriarProjetoExistente() {
		ProjetoDTO projeto = instanciaProjeto();

		Mockito.when(projetoRepository.existsByNome(projeto.getNome())).thenReturn(true);

		MensagemDTO mensagemRecebida = projetoService.criaProjeto(projeto);
		MensagemDTO mensagemEsperada = PROJETO_EXISTENTE;

		Assert.assertEquals("Não deve criar projeto já existente", mensagemEsperada, mensagemRecebida);

	}

	@Test
	public void naoDeveCriarProjetoComIdSecretariaInexistente() {
		ProjetoDTO projeto = instanciaProjeto();

		Mockito.when(projetoRepository.existsByNome(projeto.getNome())).thenReturn(false);

		Optional<Secretaria> secretariaBD = Optional.empty();

		Mockito.when(secretariaRepository.findById(projeto.getIdSecretaria())).thenReturn(secretariaBD);

		MensagemDTO mensagemRecebida = projetoService.criaProjeto(projeto);
		MensagemDTO mensagemEsperada = SECRETARIA_INEXISTENTE;

		Assert.assertEquals("Não deve criar projeto com idSecretaria inexistente", mensagemEsperada, mensagemRecebida);

	}

	@Test
	public void naoDeveCriarProjetoEmSecretariaSemOrcamento() {
		ProjetoDTO projeto = instanciaProjeto();
		projeto.setCusto(10000000);

		Optional<Secretaria> secretariaBD = instanciaOptionalSecretaria();

		Mockito.when(projetoRepository.existsByNome(projeto.getNome())).thenReturn(false);

		Mockito.when(secretariaRepository.findById(projeto.getIdSecretaria())).thenReturn(secretariaBD);

		MensagemDTO mensagemRecebida = projetoService.criaProjeto(projeto);
		MensagemDTO mensagemEsperada = new MensagemDTO("Orçamento insuficiente para o projeto");

		Assert.assertEquals("Não deve criar projeto em secretaria com orçamento insufiente para projetos",
				mensagemEsperada, mensagemRecebida);

	}

	@Test
	public void deveAlterarProjetoComSucesso() {
		AlteraProjetoDTO alteracao = new AlteraProjetoDTO();
		alteracao.setNovaDescricao("Constuir dois novos hospitais na cidade");
		
		Optional<Projeto> projetoOptional = insciaOptionalProjeto();
		
		Mockito.when(projetoRepository.findById(11L)).thenReturn(projetoOptional);

		MensagemDTO mensagemRecebida = projetoService.alteraProjeto(11L, alteracao);
		MensagemDTO mensagemEsperada = new MensagemDTO("Projeto alterado com sucesso");

		Assert.assertEquals("Deve alterar um projeto com sucesso",
				mensagemEsperada, mensagemRecebida);

	}

	@Test
	public void naoDeveAlterarProjetoInexistente() {
		AlteraProjetoDTO alteracao = new AlteraProjetoDTO();
		alteracao.setNovaDescricao("Constuir dois novos hospitais na cidade");
		
		Optional<Projeto> projetoOptional = Optional.empty();
		
		Mockito.when(projetoRepository.findById(11L)).thenReturn(projetoOptional);

		MensagemDTO mensagemRecebida = this.projetoService.alteraProjeto(11L, alteracao);
		MensagemDTO mensagemEsperada = new MensagemDTO("Projeto não encontrado");

		Assert.assertEquals("Não deve alterar um projeto inexistente",
				mensagemEsperada, mensagemRecebida);

	}
	
	@Test
	public void deveConcluirProjetoComSucesso() {
		ProjetoConcluidoDTO concluiProjeto = new ProjetoConcluidoDTO();
		concluiProjeto.setDataTerminoProjeto(LocalDate.of(2021, 1, 1));
		
		Optional<Projeto> projetoOptional = insciaOptionalProjeto();
		
		Mockito.when(projetoRepository.findById(11L)).thenReturn(projetoOptional);
		
		
		MensagemDTO mensagemRecebida = this.projetoService.projetoConcluido(11L, concluiProjeto);
		MensagemDTO mensagemEsperada = PROJETO_CONCLUIDO;
		
		Assert.assertEquals("Deve concluir o projeto com sucesso", mensagemEsperada, mensagemRecebida);
	}
	
	@Test
	public void naoDeveConcluirProjetoInexistente() {
		ProjetoConcluidoDTO concluiProjeto = new ProjetoConcluidoDTO();
		concluiProjeto.setDataTerminoProjeto(LocalDate.of(2021, 1, 1));
		
		Optional<Projeto> projetoOptional = Optional.empty();
		
		Mockito.when(projetoRepository.findById(11L)).thenReturn(projetoOptional);
		
		MensagemDTO mensagemRecebida = this.projetoService.projetoConcluido(11L, concluiProjeto);
		MensagemDTO mensagemEsperada = PROJETO_INEXISTENTE;
		
		Assert.assertEquals("Não deve concluir projeto inexistente", mensagemEsperada, mensagemRecebida);
	}
	
	@Test
	public void naoDeveConcluirProjetoComDataDeEntregaMaiorQueInicio() {
		ProjetoConcluidoDTO concluiProjeto = new ProjetoConcluidoDTO();
		concluiProjeto.setDataTerminoProjeto(LocalDate.of(2019, 1, 1));
		
		Optional<Projeto> projetoOptional = insciaOptionalProjeto();
		
		Mockito.when(projetoRepository.findById(11L)).thenReturn(projetoOptional);
		
		MensagemDTO mensagemRecebida = this.projetoService.projetoConcluido(11L, concluiProjeto);
		MensagemDTO mensagemEsperada = DATA_DE_INICIO_MAIOR_QUE_ENTREGA;
		
		Assert.assertEquals("Não deve concluir projeto com a data de entrega maior que início", mensagemEsperada, mensagemRecebida);
	}
	
	private Optional<Projeto> insciaOptionalProjeto() {
		Projeto projeto = new Projeto();
		projeto.setIdProjeto(11L);
		projeto.setDataInicio(LocalDate.of(2020, 1, 1));
		projeto.setCusto(2000);
		projeto.setDescricao("constuir um posto de atendimento");
		Secretaria secretaria = instanciaOptionalSecretaria().get();
		projeto.setSecretaria(secretaria);
		projeto.setConcluido(false);
		projeto.setNome("hospital");
		projeto.setDataEntrega(null);
		Optional<Projeto> projetoOptional = Optional.of(projeto);
		
		return projetoOptional;
	}

	private ProjetoDTO instanciaProjeto() {
		ProjetoDTO projeto = new ProjetoDTO();
		projeto.setCusto(100000);
		projeto.setDescricao("Constuir um novo hospital na cidade");
		projeto.setIdSecretaria(10L);
		projeto.setNome("Novo hospital");

		return projeto;
	}

	private Optional<Secretaria> instanciaOptionalSecretaria() {
		Secretaria secretaria = new Secretaria();
		secretaria.setArea(Area.SAUDE);
		secretaria.setEmail("saude@am.com.br");
		secretaria.setEndereco("Av. Oscar Borel");
		secretaria.setOrcamentoFolha(200000);
		secretaria.setOrcamentoProjetos(100000);
		secretaria.setSite("www.saudeam.com.br");
		secretaria.setTelefone("(092) 99999-0000 ");

		Optional<Secretaria> secretariaBD = Optional.of(secretaria);

		return secretariaBD;
	}
}

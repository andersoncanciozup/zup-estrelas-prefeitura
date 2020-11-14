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

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraFuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Funcionario;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.enums.Area;
import br.com.zup.estrelas.sistema.prefeitura.repository.FuncionarioRepository;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@RunWith(MockitoJUnitRunner.class)
public class FuncionarioServiceTestes {

	private static final MensagemDTO SALARIO_INFERIOR_AO_ANTERIOR = new MensagemDTO("Salário não pode ser infeior ao anterior");

	private static final MensagemDTO FUNCIONARIO_ALTERADO_COM_SUCESSO = new MensagemDTO("Funcionario alterado com sucesso");

	private static final MensagemDTO FUNCIONARIO_INEXISTENTE = new MensagemDTO("Funcionário inexistente");

	private static final MensagemDTO FUNCIONARIO_REMOVIDO_COM_SUCESSO = new MensagemDTO("Funcionário removido com sucesso");

	private static final MensagemDTO SALARIO_MAIOR_DO_FOLHA_DE_PAGAMENTO = new MensagemDTO("Salário do funcionário acima da folha de pagamento da secretaria");

	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não existente");

	private static final MensagemDTO FUNCIONARIO_JA_EXISTENTE = new MensagemDTO("Funcionario já cadastrado");

	private static final MensagemDTO FUNCIONARIO_CADASTRADO_COM_SUCESSO = new MensagemDTO("Funcionario cadastrado com sucesso");

	@Mock
	FuncionarioRepository funcionarioRepository;
	
	@Mock
	SecretariaRepository secretariaRepository;
	
	@InjectMocks
	FuncionarioService funcionarioService;
	
	private FuncionarioDTO instanciaFuncionarioDTO() {
		FuncionarioDTO funcionario = new FuncionarioDTO();
		funcionario.setConcursado(true);
		funcionario.setCpf("000000000-00");
		funcionario.setDataAdmissao(LocalDate.of(2020, 9, 10));
		funcionario.setFuncao("Professor");
		funcionario.setNome("Anderson Cancio");
		funcionario.setSalario(3000);
		funcionario.setIdSecretaria(11L);
		return funcionario;
	}
	
	private Optional<Secretaria> instanciaSecretariaOptional() {
		Secretaria secretaria = new Secretaria();
		secretaria.setArea(Area.SAUDE);
		secretaria.setEmail("saude@am.com.br");
		secretaria.setEndereco("Av. Oscar Borel");
		secretaria.setOrcamentoFolha(200000);
		secretaria.setOrcamentoProjetos(100000.00);
		secretaria.setSite("www.saudeam.com.br");
		secretaria.setTelefone("(092) 99999-0000 ");
		
		Optional<Secretaria> secretariaBD = Optional.of(secretaria);
		
		return secretariaBD;
	}
		
	@Test
	public void deveCriarFuncionarioComSucesso() {
		FuncionarioDTO funcionario = instanciaFuncionarioDTO();
		
		Mockito.when(funcionarioRepository.existsByCpf(funcionario.getCpf())).thenReturn(false);
		
		Optional<Secretaria> secretariaBD = instanciaSecretariaOptional();
		Mockito.when(secretariaRepository.findById(funcionario.getIdSecretaria())).thenReturn(secretariaBD);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.criaFuncionario(funcionario);
		MensagemDTO mensagemEsperada = FUNCIONARIO_CADASTRADO_COM_SUCESSO;
	
		Assert.assertEquals("Deve criar um funcionario com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveCriarFuncionarioExistente() {
		FuncionarioDTO funcionario = instanciaFuncionarioDTO();
		
		Mockito.when(funcionarioRepository.existsByCpf(funcionario.getCpf())).thenReturn(true);
				
		MensagemDTO mensagemRetornada = this.funcionarioService.criaFuncionario(funcionario);
		MensagemDTO mensagemEsperada = FUNCIONARIO_JA_EXISTENTE;
	
		Assert.assertEquals("Não deve criar um funcionario já existente", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveCriarFuncionarioComIdSecretariaInexistente() {
		FuncionarioDTO funcionario = instanciaFuncionarioDTO();
		
		Mockito.when(funcionarioRepository.existsByCpf(funcionario.getCpf())).thenReturn(false);
		
		Optional<Secretaria> secretariaBD = Optional.empty();
		Mockito.when(secretariaRepository.findById(funcionario.getIdSecretaria())).thenReturn(secretariaBD);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.criaFuncionario(funcionario);
		MensagemDTO mensagemEsperada = SECRETARIA_INEXISTENTE;
	
		Assert.assertEquals("Não deve criar um funcionario com idSecretaria inexistente ", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveCriarFuncionarioSeSecretariarNaoTiverOrcamento() {
		FuncionarioDTO funcionario = instanciaFuncionarioDTO();
		funcionario.setSalario(300000);
		
		Mockito.when(funcionarioRepository.existsByCpf(funcionario.getCpf())).thenReturn(false);
				
		Optional<Secretaria> secretariaBD = instanciaSecretariaOptional();
		Mockito.when(secretariaRepository.findById(funcionario.getIdSecretaria())).thenReturn(secretariaBD);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.criaFuncionario(funcionario);
		MensagemDTO mensagemEsperada = SALARIO_MAIOR_DO_FOLHA_DE_PAGAMENTO;
	
		Assert.assertEquals("Não deve criar funcionario se a secretaria não tiver orçamento suficiente", mensagemEsperada, mensagemRetornada);
		
	}

	@Test
	public void deveRemoverFuncionarioComSucesso() {
		
		Mockito.when(funcionarioRepository.existsById(11L)).thenReturn(true);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.removeFuncionario(11L);
		MensagemDTO mensagemEsperada = FUNCIONARIO_REMOVIDO_COM_SUCESSO;
		
		Assert.assertEquals("Deve remover um funcionario com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveRemoverFuncionarioInexistente() {
		
		Mockito.when(funcionarioRepository.existsById(11L)).thenReturn(false);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.removeFuncionario(11L);
		MensagemDTO mensagemEsperada = FUNCIONARIO_INEXISTENTE;
		
		Assert.assertEquals("Não deve remover funcionario inexistente", mensagemEsperada, mensagemRetornada);
	}
	
	private AlteraFuncionarioDTO instanciaAlteraFuncionario() {
		AlteraFuncionarioDTO alteracaoFuncionario = new AlteraFuncionarioDTO();
		alteracaoFuncionario.setConcursado(true);
		alteracaoFuncionario.setFuncao("Coordenador");
		alteracaoFuncionario.setIdSecretaria(1L);
		alteracaoFuncionario.setNome("Anderson Cancio");
		alteracaoFuncionario.setSalario(10000);
		return alteracaoFuncionario;
	}
	
	@Test
	public void deveAlterarFuncionarioComSucesso() {
		AlteraFuncionarioDTO alteracaoFuncionario = instanciaAlteraFuncionario();
		
		Optional<Secretaria> secretariaBD = instanciaSecretariaOptional();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setConcursado(true);
		funcionario.setCpf("000000000-00");
		funcionario.setDataAdmissao(LocalDate.of(2020, 9, 10));
		funcionario.setFuncao("Professor");
		funcionario.setNome("Anderson Cancio");
		funcionario.setSalario(3000);
		funcionario.setSecretaria(secretariaBD.get());
		
		Optional<Funcionario> funcionarioBD = Optional.of(funcionario);
		
		
		Mockito.when(funcionarioRepository.findById(10L)).thenReturn(funcionarioBD);
				
		Mockito.when(secretariaRepository.findById(alteracaoFuncionario.getIdSecretaria())).thenReturn(secretariaBD);
		
		Mockito.when(secretariaRepository.findById(funcionario.getSecretaria().getIdSecretaria())).thenReturn(secretariaBD);
		
		MensagemDTO mensagemRetornada = this.funcionarioService.alteraFuncionario(10L, alteracaoFuncionario);
		MensagemDTO mensagemEsperada = FUNCIONARIO_ALTERADO_COM_SUCESSO;	
	
		Assert.assertEquals("Deve criar um funcionario com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveAlterarFuncionarioInexistente() {
		AlteraFuncionarioDTO alteracaoFuncionario = instanciaAlteraFuncionario();
					
		Optional<Funcionario> funcionarioBD = Optional.empty();
		
		Mockito.when(funcionarioRepository.findById(10L)).thenReturn(funcionarioBD);
					
		MensagemDTO mensagemRetornada = this.funcionarioService.alteraFuncionario(10L, alteracaoFuncionario);
		MensagemDTO mensagemEsperada = FUNCIONARIO_INEXISTENTE;
	
		Assert.assertEquals("Não deve alterar um funcionario inexistente", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveAlterarFuncionarioParaSecretariaInexistente() {
		AlteraFuncionarioDTO alteracaoFuncionario = instanciaAlteraFuncionario();
		
		Secretaria secretaria = new Secretaria();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setConcursado(true);
		funcionario.setCpf("000000000-00");
		funcionario.setDataAdmissao(LocalDate.of(2020, 9, 10));
		funcionario.setFuncao("Professor");
		funcionario.setNome("Anderson Cancio");
		funcionario.setSalario(3000);
		funcionario.setSecretaria(secretaria);
		
		Optional<Funcionario> funcionarioBD = Optional.of(funcionario);
				
		Mockito.when(funcionarioRepository.findById(10L)).thenReturn(funcionarioBD);
	
		Optional<Secretaria> secretariaBD = Optional.empty();
		
		Mockito.when(secretariaRepository.findById(alteracaoFuncionario.getIdSecretaria())).thenReturn(secretariaBD);
				
		MensagemDTO mensagemRetornada = this.funcionarioService.alteraFuncionario(10L, alteracaoFuncionario);
		MensagemDTO mensagemEsperada = SECRETARIA_INEXISTENTE;	
	
		Assert.assertEquals("Não deve alterar um funcionario para uma secretaria inxistente", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveAlterarFuncionarioParaSecretariaSemOrcamentoFolha() {
		AlteraFuncionarioDTO alteracaoFuncionario = instanciaAlteraFuncionario();
		alteracaoFuncionario.setSalario(5000000);
		
		Optional<Secretaria> secretariaBD = instanciaSecretariaOptional();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setConcursado(true);
		funcionario.setCpf("000000000-00");
		funcionario.setDataAdmissao(LocalDate.of(2020, 9, 10));
		funcionario.setFuncao("Professor");
		funcionario.setNome("Anderson Cancio");
		funcionario.setSalario(3000);
		funcionario.setSecretaria(secretariaBD.get());
		
		Optional<Funcionario> funcionarioBD = Optional.of(funcionario);
		
		Mockito.when(funcionarioRepository.findById(10L)).thenReturn(funcionarioBD);
		
		Mockito.when(secretariaRepository.findById(alteracaoFuncionario.getIdSecretaria())).thenReturn(secretariaBD);
				
		MensagemDTO mensagemRetornada = this.funcionarioService.alteraFuncionario(10L, alteracaoFuncionario);
		MensagemDTO mensagemEsperada = SALARIO_MAIOR_DO_FOLHA_DE_PAGAMENTO;
	
		Assert.assertEquals("Não deve alterar funcionario para uma secretaria com sem orçamento para pagamento do salário", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveAlterarFuncionarioComSalarioInfeior() {
		AlteraFuncionarioDTO alteracaoFuncionario = instanciaAlteraFuncionario();
		alteracaoFuncionario.setSalario(1000);
		
		Optional<Secretaria> secretariaBD = instanciaSecretariaOptional();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setConcursado(true);
		funcionario.setCpf("000000000-00");
		funcionario.setDataAdmissao(LocalDate.of(2020, 9, 10));
		funcionario.setFuncao("Professor");
		funcionario.setNome("Anderson Cancio");
		funcionario.setSalario(3000);
		funcionario.setSecretaria(secretariaBD.get());
		
		Optional<Funcionario> funcionarioBD = Optional.of(funcionario);
		
		Mockito.when(funcionarioRepository.findById(10L)).thenReturn(funcionarioBD);

		Mockito.when(secretariaRepository.findById(alteracaoFuncionario.getIdSecretaria())).thenReturn(secretariaBD);
				
		MensagemDTO mensagemRetornada = this.funcionarioService.alteraFuncionario(10L, alteracaoFuncionario);
		MensagemDTO mensagemEsperada = SALARIO_INFERIOR_AO_ANTERIOR;
	
		Assert.assertEquals("DNão deve alterar funcionario com salário inferior", mensagemEsperada, mensagemRetornada);
	}
}

package br.com.zup.estrelas.sistema.prefeitura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.sistema.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraFuncionarioDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Funcionario;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.repository.FuncionarioRepository;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@Service
public class FuncionarioService implements IFuncionarioService {

	private static final MensagemDTO FUNCIONARIO_REMOVIDO_COM_SUCESSO = new MensagemDTO("Funcionário removido com sucesso");

	private static final MensagemDTO FUNCIONARIO_ALTERADO_COM_SUCESSO = new MensagemDTO("Funcionario alterado com sucesso");

	private static final MensagemDTO SALARIO_INFERIOR_AO_ATUAL = new MensagemDTO("Salário não pode ser infeior ao anterior");

	private static final MensagemDTO FUNCIONARIO_INEXISTENTE = new MensagemDTO("Funcionário inexistente");

	private static final MensagemDTO FUNCIONARIO_CRIADO_COM_SUCESSO = new MensagemDTO("Funcionario cadastrado com sucesso");

	private static final MensagemDTO SEM_ORCAMENTO_PARA_PAGAMENTO = new MensagemDTO("Salário do funcionário acima da folha de pagamento da secretaria");

	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não existente");

	private static final MensagemDTO FUNCIONARIO_JA_CADASTRADO = new MensagemDTO("Funcionario já cadastrado");

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	SecretariaRepository secretariaRepository;
	
	@Autowired
	SecretariaService secretariaService;
	
	
	public MensagemDTO criaFuncionario(FuncionarioDTO funcionario) {
		if(verificaSefuncionarioJaExiste(funcionario)) {		
			return FUNCIONARIO_JA_CADASTRADO;	
		}
		
		Optional<Secretaria> secretariaConsultada = secretariaRepository.findById(funcionario.getIdSecretaria());

		if(secretariaConsultada.isEmpty()) {
			return SECRETARIA_INEXISTENTE;
		}
		
		Secretaria secretaria = secretariaConsultada.get();
		
		if(funcionario.getSalario() > secretaria.getOrcamentoFolha() ) {
			return SEM_ORCAMENTO_PARA_PAGAMENTO;
		} 
		
		double novoOrcamentoFolha = secretaria.getOrcamentoFolha() - funcionario.getSalario();
		armazenaNovoOrcamentoSecretaria(novoOrcamentoFolha, secretaria);
		
		armazenaNovoFuncionario(funcionario, secretaria);
		
		return FUNCIONARIO_CRIADO_COM_SUCESSO;
	}
	
	public Funcionario consultaFuncionario(Long idFuncionario) {
		Optional<Funcionario> funcionarioConsultado = funcionarioRepository.findById(idFuncionario);
		
		if(funcionarioConsultado.isEmpty()) {
			return new Funcionario();
		}

		Funcionario funcionario = funcionarioConsultado.get();

		return funcionario;
	}

	public MensagemDTO alteraFuncionario(Long idFuncionario, AlteraFuncionarioDTO alteracaoFuncionario) {
		
		Optional<Funcionario> funcionarioConsultado = funcionarioRepository.findById(idFuncionario);

		if (funcionarioConsultado.isEmpty()) {
			return FUNCIONARIO_INEXISTENTE;	
		}
		
		Optional<Secretaria> secretariaConsultada = secretariaRepository.findById(alteracaoFuncionario.getIdSecretaria());

		if(secretariaConsultada.isEmpty()) {
			return SECRETARIA_INEXISTENTE;
		}
		
		Secretaria secretaria = secretariaConsultada.get();
		
		if(secretaria.getOrcamentoFolha() < alteracaoFuncionario.getSalario()) {
			return SEM_ORCAMENTO_PARA_PAGAMENTO;
		}
		
		Funcionario funcionario = funcionarioConsultado.get();
		
		if(alteracaoFuncionario.getSalario() < funcionario.getSalario()) {
			return SALARIO_INFERIOR_AO_ATUAL;
		}
		
		if(alteracaoFuncionario.getIdSecretaria() != funcionario.getSecretaria().getIdSecretaria()) {
			double novoOrcamentoFolhaSecretariaNova = secretaria.getOrcamentoFolha() - alteracaoFuncionario.getSalario();
			alteraOrcamentoDaNovaSecretaria(novoOrcamentoFolhaSecretariaNova, secretaria);
			
			Secretaria secretariaAnterior = secretariaRepository.findById(funcionario.getSecretaria().getIdSecretaria()).get();
			double novoOrcamentoFolhaSecretariaAnterior = secretariaAnterior.getOrcamentoFolha() + funcionario.getSalario();
			alteraOrcamentoDaSecretariaAnterior(novoOrcamentoFolhaSecretariaAnterior, secretariaAnterior);
		}
		
		if(alteracaoFuncionario.getIdSecretaria() == funcionario.getSecretaria().getIdSecretaria()) {
			Secretaria secretariaAnterior = secretariaRepository.findById(funcionario.getSecretaria().getIdSecretaria()).get();
			double novoOrcamentoFolhaDaSecretaria = secretariaAnterior.getOrcamentoFolha() - (alteracaoFuncionario.getSalario() - funcionario.getSalario());
			alteraOrcamentoDaSecretaria(secretariaAnterior, novoOrcamentoFolhaDaSecretaria);
		}
		
		BeanUtils.copyProperties(alteracaoFuncionario, funcionario);
		funcionarioRepository.save(funcionario);
		
		return FUNCIONARIO_ALTERADO_COM_SUCESSO;	
		
	}
	
	public MensagemDTO removeFuncionario(Long idFuncionario) {
		if(funcionarioRepository.existsById(idFuncionario)) {
			funcionarioRepository.deleteById(idFuncionario);
			return FUNCIONARIO_REMOVIDO_COM_SUCESSO;
		}
		
		return FUNCIONARIO_INEXISTENTE;
	}

	public List<Funcionario> listaFuncionarios() {
		List<Funcionario> funcionarios = (List<Funcionario>) this.funcionarioRepository.findAll();
		
		return funcionarios;
	}

	private boolean verificaSefuncionarioJaExiste(FuncionarioDTO funcionario) {
		if(funcionarioRepository.existsByCpf(funcionario.getCpf())) {
			return true;
		} 
		return false;
	}
	
	private void armazenaNovoOrcamentoSecretaria(Double novoOrcamentoFolha, Secretaria secretaria) {
		secretaria.setOrcamentoFolha(novoOrcamentoFolha);
		secretariaRepository.save(secretaria);		
	}
	
	private void armazenaNovoFuncionario(FuncionarioDTO funcionario, Secretaria secretaria) {
		Funcionario novoFuncionario = new Funcionario();
		BeanUtils.copyProperties(funcionario, novoFuncionario);
		novoFuncionario.setSecretaria(secretaria);
		funcionarioRepository.save(novoFuncionario);
	}

	private void alteraOrcamentoDaSecretaria(Secretaria secretariaAnterior, double novoOrcamentoFolhaDaSecretaria) {
		secretariaAnterior.setOrcamentoFolha(novoOrcamentoFolhaDaSecretaria);
		secretariaRepository.save(secretariaAnterior);
	}
	
	private void alteraOrcamentoDaSecretariaAnterior(double novoOrcamentoFolhaSecretariaAnterior, Secretaria secretariaAnterior) {
		secretariaAnterior.setOrcamentoFolha(novoOrcamentoFolhaSecretariaAnterior);
		secretariaRepository.save(secretariaAnterior);
	}
	
	private void alteraOrcamentoDaNovaSecretaria(double novoOrcamentoFolhaSecretariaNova, Secretaria secretaria) {
		secretaria.setOrcamentoFolha(novoOrcamentoFolhaSecretariaNova);
		secretariaRepository.save(secretaria);
	}



}

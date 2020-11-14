package br.com.zup.estrelas.sistema.prefeitura.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.sistema.prefeitura.dto.AlteraProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoConcluidoDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Projeto;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.repository.ProjetoRepository;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@Service
public class ProjetoService implements IProjetoService {

	private static final MensagemDTO PROJETO_CONCLUIDO = new MensagemDTO("Projeto concluido com sucesso");

	private static final MensagemDTO DATA_DE_INICIO_MAIOR_QUE_ENTREGA = new MensagemDTO("A data da entrega não pode ser maior do que início");

	private static final MensagemDTO PROJETO_ALTERADO_COM_SUCESSO = new MensagemDTO("Projeto alterado com sucesso");

	private static final MensagemDTO PROJETO_INEXISTENTE = new MensagemDTO("Projeto não encontrado");

	private static final MensagemDTO PROJETO_CRIADO_COM_SUCESSO = new MensagemDTO("Projeto criado com sucesso");

	private static final MensagemDTO ORCAMENTO_INSUFICIENTE = new MensagemDTO("Orçamento insuficiente para o projeto");

	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não existente");

	private static final MensagemDTO PROJETO_EXISTENTE = new MensagemDTO("Projeto com o mesmo nome já existente");

	@Autowired
	ProjetoRepository projetoRepository;

	@Autowired
	SecretariaRepository secretariaRepository;

	public MensagemDTO criaProjeto(ProjetoDTO projeto) {
		if(projetoRepository.existsByNome(projeto.getNome())) {
			return PROJETO_EXISTENTE;
		}
		
		Optional<Secretaria> secretariaConsultada = secretariaRepository.findById(projeto.getIdSecretaria());

		if(secretariaConsultada.isEmpty()) {
			return SECRETARIA_INEXISTENTE;
		}
		
		Secretaria secretaria = secretariaConsultada.get();
		
		if(secretaria.getOrcamentoProjetos() < projeto.getCusto()) {
			return ORCAMENTO_INSUFICIENTE;
		}
		
		double novoOrcamentoProjetos = secretaria.getOrcamentoProjetos() - projeto.getCusto();
		secretaria.setOrcamentoProjetos(novoOrcamentoProjetos);
		secretariaRepository.save(secretaria);
		
		armazenaNovoProjeto(projeto, secretaria);
		return PROJETO_CRIADO_COM_SUCESSO;
	}
	


	public Projeto consultaProjeto(Long idProjeto) {
		Optional<Projeto> projetoConsultado = projetoRepository.findById(idProjeto);
			if(projetoConsultado.isEmpty()) {
			return new Projeto();
		}
		
		Projeto projeto = projetoConsultado.get();
		return projeto;
	}

	public MensagemDTO alteraProjeto(Long idProjeto, AlteraProjetoDTO alteracao) {
		Optional<Projeto> projetoConsultado = projetoRepository.findById(idProjeto);
		
		if(projetoConsultado.isEmpty()) {
			return PROJETO_INEXISTENTE;
		}

		Projeto projeto = projetoConsultado.get();
		BeanUtils.copyProperties(alteracao, projeto);
		projetoRepository.save(projeto);
		return PROJETO_ALTERADO_COM_SUCESSO;
	}

	public List<Projeto> listaProjetos() {
		List<Projeto> consultaProjetos = (List<Projeto>) projetoRepository.findAll();
		return consultaProjetos;
	}
	
	public MensagemDTO projetoConcluido(Long idProjeto, ProjetoConcluidoDTO dataTermino) {
		Optional<Projeto> projetoConsultado = projetoRepository.findById(idProjeto);
		if(projetoConsultado.isEmpty()) {
			return PROJETO_INEXISTENTE;
		}
		
		Projeto projeto = projetoConsultado.get();
		
		Period periodo = Period.between(projeto.getDataInicio(), dataTermino.getDataTerminoProjeto());
				
		if(periodo.isNegative()) {
			return DATA_DE_INICIO_MAIOR_QUE_ENTREGA;
		}
		
		projeto.setConcluido(true);
		projeto.setDataEntrega(dataTermino.getDataTerminoProjeto());
		
		projetoRepository.save(projeto);	
		return PROJETO_CONCLUIDO;
	}
	
	private void armazenaNovoProjeto(ProjetoDTO projeto, Secretaria secretaria) {
		Projeto novoProjeto = new Projeto();
		BeanUtils.copyProperties(projeto, novoProjeto);
		novoProjeto.setSecretaria(secretaria);
		novoProjeto.setDataInicio(LocalDate.now());
		novoProjeto.setConcluido(false);
		projetoRepository.save(novoProjeto);
	}

}

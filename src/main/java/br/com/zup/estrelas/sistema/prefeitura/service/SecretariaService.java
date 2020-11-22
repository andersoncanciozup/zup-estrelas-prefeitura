package br.com.zup.estrelas.sistema.prefeitura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.sistema.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.sistema.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.sistema.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.sistema.prefeitura.repository.SecretariaRepository;

@Service
public class SecretariaService implements ISecretariaService {

	private static final MensagemDTO SECRETARIA_REMOVIDA_COM_SUCESSO = new MensagemDTO("Secretaria removida com sucesso");
	private static final MensagemDTO SECRETARIA_ALTERADA_COM_SUCESSO = new MensagemDTO("Secretaria alterada com sucesso");
	private static final MensagemDTO SECRETARIA_INEXISTENTE = new MensagemDTO("Secretaria não encontrada");
	private static final MensagemDTO SECRETARIA_CADASTRADA_COM_SUCESSO = new MensagemDTO("Secretaria cadastrada com sucesso");
	private static final MensagemDTO SECRETARIA_EXISTENTE = new MensagemDTO("Secretaria já existente");
	
	@Autowired
	SecretariaRepository secretariaRepository;

	public MensagemDTO criaSecretaria(SecretariaDTO cadastroSecretariaDTO) {
		if (secretariaRepository.existsByArea(cadastroSecretariaDTO.getArea())) {
			return SECRETARIA_EXISTENTE;
		}

		Secretaria secretaria = new Secretaria();
		BeanUtils.copyProperties(cadastroSecretariaDTO, secretaria);
		secretariaRepository.save(secretaria);
		return SECRETARIA_CADASTRADA_COM_SUCESSO;
	}

	public Secretaria consultaSecretaria(Long idSecretaria) {
		Optional<Secretaria> secretariaConsulta = secretariaRepository.findById(idSecretaria);
		if (secretariaConsulta.isEmpty()) {
			return new Secretaria();		
		}

		Secretaria secretaria = secretariaConsulta.get();

		return secretaria;
	}

	public MensagemDTO alteraSecretaria(Long idSecretaria, SecretariaDTO alteracaoSecretariaDTO) {
		Optional<Secretaria> secretariaConsulta = secretariaRepository.findById(idSecretaria);
		if (secretariaConsulta.isEmpty()) {	
			return SECRETARIA_INEXISTENTE;
		}

		Secretaria secretariaAlterada = secretariaConsulta.get();
		BeanUtils.copyProperties(alteracaoSecretariaDTO, secretariaAlterada);
		secretariaRepository.save(secretariaAlterada);
		return SECRETARIA_ALTERADA_COM_SUCESSO;
	}

	public MensagemDTO removeSecretaria(Long idSecretaria) {
		if(secretariaRepository.existsById(idSecretaria)) {
			secretariaRepository.deleteById(idSecretaria);;
			return SECRETARIA_REMOVIDA_COM_SUCESSO;
		}
		return SECRETARIA_INEXISTENTE;
	}

	public List<Secretaria> listaSecretaria() {
	    //TODO: Aqui poderia ser um return direto.
		List<Secretaria> consultaSecretarias = (List<Secretaria>) secretariaRepository.findAll();
		return consultaSecretarias;
	}
}

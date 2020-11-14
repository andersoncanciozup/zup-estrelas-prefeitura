package br.com.zup.estrelas.sistema.prefeitura.dto;

import java.time.LocalDate;

public class ProjetoConcluidoDTO {
	
	private LocalDate dataTerminoProjeto;

	public LocalDate getDataTerminoProjeto() {
		return dataTerminoProjeto;
	}

	public void setDataTerminoProjeto(LocalDate dataTerminoProjeto) {
		this.dataTerminoProjeto = dataTerminoProjeto;
	}

}

package br.com.zup.estrelas.sistema.prefeitura.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.zup.estrelas.sistema.prefeitura.enums.Area;

public class SecretariaDTO {
	
    @Enumerated(EnumType.STRING)
	private Area area;
	private double orcamentoProjetos;
	private double orcamentoFolha;
	private String telefone;
	private String endereco;
	private String site;
	private String email;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public double getOrcamentoProjetos() {
		return orcamentoProjetos;
	}

	public void setOrcamentoProjetos(double orcamentoProjetos) {
		this.orcamentoProjetos = orcamentoProjetos;
	}

	public double getOrcamentoFolha() {
		return orcamentoFolha;
	}

	public void setOrcamentoFolha(double orcamentoFolha) {
		this.orcamentoFolha = orcamentoFolha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	@Override
//	public String toString() {
//		return "SecretariaDTO [area=" + area + ", orcamentoProjetos=" + orcamentoProjetos + ", orcamentoFolha="
//				+ orcamentoFolha + ", telefone=" + telefone + ", endereco=" + endereco + ", site=" + site + ", email="
//				+ email + "]";
//	}
//	
	

}

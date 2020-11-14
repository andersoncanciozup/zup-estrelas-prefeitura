package br.com.zup.estrelas.sistema.prefeitura.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "funcionario")
public class Funcionario {

	@Id
	@Column(name = "id_funcionario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFuncionario;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false)
	private double salario;

	@ManyToOne
	@JoinColumn(name = "id_secretaria", nullable = false, foreignKey = @ForeignKey(name = "FK_funcionario"))
	@JsonBackReference
	private Secretaria secretaria;
	
	@Column(nullable = false)
	private String funcao;

	@Column(nullable = false)
	private boolean concursado;

	@Column(nullable = false)
	private LocalDate dataAdmissao;

	public long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public boolean isConcursado() {
		return concursado;
	}

	public void setConcursado(boolean concursado) {
		this.concursado = concursado;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

}

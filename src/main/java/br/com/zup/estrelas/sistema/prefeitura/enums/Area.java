package br.com.zup.estrelas.sistema.prefeitura.enums;

public enum Area {

	SAUDE("saude"), EDUCACAO("educacao"), ESPORTE("esporte"), TRANSITO("transito"), OBRAS("obras");
	
    private String value;

    Area(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
	
}

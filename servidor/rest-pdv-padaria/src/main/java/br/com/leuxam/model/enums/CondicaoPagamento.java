package br.com.leuxam.model.enums;

public enum CondicaoPagamento {
	
	PIX(1),
	CREDITO(2),
	DEBITO(3),
	DINHEIRO(4),
	NULL(5);
	
	private int code;
	
	private CondicaoPagamento(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static CondicaoPagamento valueOf(int code) {
		for (CondicaoPagamento value : CondicaoPagamento.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Codigo está invalido!!");
	}
}

package com.estudo.api.model.enuns;



public enum PaymentEnum {

	ATIVO(1, "Ativo"),
	CANCELADO(2, "Cancelado"),
	PENDENTE(3, "Pendente"),
	SEM_ASSINATURA(4, "Sem assinatura");
	
	private Integer cod;
	private String description;
	
	private PaymentEnum(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescription() {
		return description;
	}

	public static PaymentEnum toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (PaymentEnum x : PaymentEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Codigo invalido: " + cod);
	}

	public static PaymentEnum toEnum(String descricao) {

		if (descricao == null) {
			return null;
		}

		for (PaymentEnum x : PaymentEnum.values()) {
			if (descricao.equals(x.getDescription())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descrição invalida: " + descricao);

	}
}

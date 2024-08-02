package API.entites.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

	AGUARDANDO_PAGAMENTO(1),
	PAGAMENTO_CONFIRMADO(2),
	EM_TRASPORTE(3),
	ENTREGUE(4);

	private final int code;

	public static OrderStatus valueOf(int code){
		for(OrderStatus status : OrderStatus.values()){
			if(status.getCode() == code){
				return status;
			}
		}
		throw new IllegalArgumentException("Codigo invalido");
	}
}

package API.entites.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {

	PEDINDO(1),
	PAGAMENTO_CONFIRMADO(2),
	CANCELAODO(3);

	private final int code;

	public static PaymentStatus valueOf(int code){
		for(PaymentStatus status : PaymentStatus.values()){
			if(status.getCode() == code){
				return status;
			}
		}
		throw new IllegalArgumentException("Codigo invalido");
	}
}

package API.entites.response;

import API.entites.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentResponse {

	private LocalDateTime data;
	private PaymentStatus status;

}

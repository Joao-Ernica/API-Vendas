package API.repository;

import API.entites.Payment;
import API.entites.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByStatus(PaymentStatus status);
}

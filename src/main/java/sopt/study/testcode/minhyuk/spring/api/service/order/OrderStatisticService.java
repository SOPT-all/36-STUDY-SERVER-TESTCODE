package sopt.study.testcode.minhyuk.spring.api.service.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.domain.order.Order;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderStatus;
import sopt.study.testcode.minhyuk.spring.api.service.mail.MailService;

@RequiredArgsConstructor
@Service
public class OrderStatisticService {

	private final OrderRepository orderRepository;
	private final MailService mailService;

	public boolean sendOrderStatisticsMail(LocalDate orderDate, String email){
		//해당 일자에 결제완료된 주문들을 가져와서
		List<Order>  orders = orderRepository.findOrdersByDateTime(
			orderDate.atStartOfDay(),
			orderDate.plusDays(1).atStartOfDay()
			, OrderStatus.PAYMENT_COMPLETED
		);
		//총 매출 합계를 계산하고
		int totalAmount = orders.stream()
			.mapToInt(Order::getTotalPrice)
			.sum();

		//메일 전송
		boolean result = mailService.sendMail(
			"no-reply@test.com",
			email,
			String.format("[매출통계] %s",orderDate),
			String.format("총 매출 합계는 %s원입니다.",totalAmount));

		if(!result){
			throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
		}

		return true;
	}
}

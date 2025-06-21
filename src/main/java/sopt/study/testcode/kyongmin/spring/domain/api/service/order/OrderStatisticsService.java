package sopt.study.testcode.kyongmin.spring.domain.api.service.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.service.mail.MailService;
import sopt.study.testcode.kyongmin.spring.domain.order.Order;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderRepository;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderStatus;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

	private final OrderRepository orderRepository;
	private final MailService mailService;

	public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
		// 해당 일자에 결제완료된 주문들을 가져오기
		List<Order> orders = orderRepository.findOrdersBy(
				orderDate.atStartOfDay(),
				orderDate.plusDays(1).atStartOfDay(),
				OrderStatus.PAYMENT_COMPLETED
		);

		// 총 매출 합계를 계산
		int totalAmount = orders.stream()
				.mapToInt(Order::getTotalPrice)
				.sum();

		// 메일 전송
		boolean result = mailService.sendMail(
				"no-reply@cafekiosk.com",
				email,
				String.format("[매출통계] %s", orderDate),
				String.format("총 매출 합계는 %s원입니다.", totalAmount)
		);

		if (!result) {
			throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
		}

		return true;
	}
}
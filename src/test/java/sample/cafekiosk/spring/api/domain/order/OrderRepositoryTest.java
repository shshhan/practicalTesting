package sample.cafekiosk.spring.api.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("하루 동안의 특정 결제 상태의 데이터를 조회한다.")
    @Test
    void findOrdersBy() {
        //given
        Product product1 = createProduct("001");
        Product product2 = createProduct("002");
        productRepository.saveAll(List.of(product1, product2));

        LocalDateTime localDT1 = LocalDateTime.of(2023, 8, 04, 16, 35);
        Order order1 = Order.create(
                List.of(product1, product2),
                localDT1
        );
        LocalDateTime localDT2 = LocalDateTime.of(2023, 8, 05, 16, 35);
        Order order2 = Order.create(
                List.of(product1),
                localDT2
        );
        orderRepository.saveAll(List.of(order1, order2));

        //when
        LocalDate startDate = LocalDate.of(2023, 8, 04);
        List<Order> orders = orderRepository.findOrdersBy(
                startDate.atStartOfDay(),
                startDate.plusDays(1).atStartOfDay(),
                INIT);

        //then
        assertThat(orders).hasSize(1)
                .extracting("orderStatus", "registeredDateTime")
                .containsExactlyInAnyOrder(
                        tuple(INIT, localDT1)
                );
    }

    private Product createProduct(String productNumber) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(1000)
                .sellingStatus(SELLING)
                .name("메뉴이름")
                .build()
                ;
    }

}
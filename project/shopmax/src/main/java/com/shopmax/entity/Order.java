package com.shopmax.entity;

import com.shopmax.constant.OrderStatus;
import com.shopmax.repository.ItemRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //db에서 order by 예약어를 사용하므로 orders라고 지정
@Getter
@Setter
@ToString
public class Order { //클래스명은 설계도이므로 복수형으로 쓰지 X

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //FK
    private Member member; //Order가 Member를 참조한다.

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true) //연관관계 주인을 설정 (Order는 주인이 아님을 설정)
    private List<OrderItem> orderItems = new ArrayList<>(); //반드시 List로 받아야 함

    //[중요] 양방향 참조 시 save를 진행할 때는 서로가 참조하는 객체를 꼭 넣어줘야 한다.
    public void addOrderITem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); //[중요] 양방향 참조 관계일 때는 orderItem 객체에도 order 객체를 셋팅
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for (OrderItem orderItem : orderItemList) {
            order.addOrderITem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }
    //총 주문 금액
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();

        }

        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        //재고를 원래대로 돌려놓는다.
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();

        }
    }


}




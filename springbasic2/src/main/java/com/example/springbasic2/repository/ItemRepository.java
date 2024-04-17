package com.example.springbasic2.repository;

import com.example.springbasic2.constant.ItemSellStatus;
import com.example.springbasic2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//JPA에서는 Repository 클래스가 Model 역할(데이터베이스와 대화)을 함
// - Repository 클래스는 JpaRepository 인터페이스를 반드시 상속받아야 함 (extends)
// - JpaRepository<>안에는 (JpaRepository에서) 사용할 엔티티 클래스, 해당 엔티티의 Pk(에 해당하는 속성의) 타입 작성
public interface ItemRepository extends JpaRepository<Item, Long> {
    //select * from item where item_nm = ? 조회 메서드
    // - 추상 메서드로 만들어줘야 함
    List<Item> findByItemNm(String itemNm);

    //select * from item where item_nm = ? and item_sell_status = ?
    List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);

    //select * from item where price ? between ?
    //매개변수 이름은 엔티티 클래스의 필드명과 꼭 똑같이 작성하지 않아도 됨
    // - JPA에서는 매개변수의 순서대로 쿼리 물음표에 값을 바인딩 함
    List<Item> findByPriceBetween(int price1, int price2);

    List<Item> findByRegTimeAfter(LocalDateTime regTime);

    //select * from where item_sell_status is not null;
    List<Item> findByItemSellStatusIsNotNull();

    //select * from where item_detail like '$?';
    List<Item> findByItemDetailEndingWith(String itemDetail);

    //2안
//    List<Item> findByItemDetailLike(String itemDetail);

    //select * from item where item_nm = ? or item_detail = ?
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //select * from item where price < ? order by desc;
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);


    //JPQL 쿼리 (findBy 메서드로 이름을 짓지 않아도 된다.)
    // - 일반 쿼리문: select * from item where item_detail = ? (일반 쿼리문은 테이블 기준)
    // - JPQL 쿼리문은 엔티티(Item) 기준 (JPQL 쿼리문은 객체 정보명 주의 필요)

//    @Query("select i from Item i where i.itemDetail = ?1 and i.itemNm - ?2") //'?1': 매개변수 > '1': 매개변수의 첫번째라는 의미
//    List<Item> findByItemDetail(String itemDetail, String itemNm);

//    @Query("select i from Item i where i.itemDetail like %?1% order by i.price desc") //?1: 매개변수에 해당
//    List<Item> findByItemDetail(String itemDetail);

    // - JPQL 쿼리문은 가급적 ? 사용 지양
//    @Query("select i from Item i where i.itemDetail = :itemDetail")
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") //?1: 매개변수에 해당
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); //매개변수로 사용할 파라미터 지정

    @Query("select i from Item i where i.price >= :price")
    List<Item> getPrice(@Param("price") int price);

    @Query("select i from Item i where itemNm like :itemNm and itemSellStatus = :itemSellStatus")
    List<Item> getItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("itemSellStatus") ItemSellStatus itemSellStatus);

    @Query("select i from Item i where i.id = :id")
    List<Item> getId(@Param("id") Long id);


    //Native Query
    @Query(value = "select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);


}

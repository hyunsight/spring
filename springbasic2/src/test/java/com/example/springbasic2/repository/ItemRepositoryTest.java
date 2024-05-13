package com.example.springbasic2.repository;

import com.example.springbasic2.constant.ItemSellStatus;
import com.example.springbasic2.entity.Item;
import com.example.springbasic2.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest //테스트용 클래스에 필수적으로 추가 필요
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {

    //ItemRepository itemRepository = new ItemRepositoryImpl();과 동일
    @Autowired
    ItemRepository itemRepository;

    //item 테이블에 insert (상품 추가)
    @Test //테스트용 junit 메서드로 지정
    @DisplayName("상품 저장 테스트") //테스트 코드 실행 시 테스트명을 지정
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        //save는 insert한 entity 객체를 그대로 return(반환)함
        Item savedItem = itemRepository.save(item); //insert
        System.out.println("insert한 엔티티 객체: " + savedItem);
    }


    //데이터 10개 저장
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item); //insert
        }
    }


    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNmTest() {
        //데이터 10개 insert
//        createItemList();

        //select * from item where item_nm = '테스트 상품3' 조회
        //커스터마이징한 find 메서드
        // - find + (엔티티이름) + By + 변수이름(= where절에서 검색할 컬럼명에 해당) + (조회할 값)
        // - ItemRepository에 추상 메서드로 만들어줘야 함
//        List<Item> itemList = itemRepository.findByItemNm("테스트 상품3");

        //select * from item 조회
        List<Item> itemList = itemRepository.findAll();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("퀴즈1-1")
    public void quiz1_1Test() {
        List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("퀴즈1-2")
    public void quiz1_2Test() {
        List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈1-3")
    public void quiz1_3Test() {
        LocalDateTime localDateTime
                = LocalDateTime.of(2023, 1, 1, 12, 12, 44);

        List<Item> itemList = itemRepository.findByRegTimeAfter(localDateTime);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈1-4")
    public void quiz1_4Test() {
        List<Item> itemList = itemRepository.findByItemSellStatusIsNotNull();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈1-5")
    public void quiz1_5Test() {
        List<Item> itemList = itemRepository.findByItemDetailEndingWith("설명1");

        // 2안
//        List<Item> itemList = itemRepository.findByItemDetailLike("%설명1"); //설명1로 끝나는
//        List<Item> itemList = itemRepository.findByItemDetailLike("설명1%"); //설명1로 시작하는
//        List<Item> itemList = itemRepository.findByItemDetailLike("%설명1%"); //설명1 글자를 포함하는

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈1-6")
    public void quiz1_6Test() {
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈1-7")
    public void quiz1_7Test() {
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("JPQL @Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세");

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈2-1")
    public void quiz2_1Test() {
        List<Item> itemList = itemRepository.getPrice(10005);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈2-2")
    public void quiz2_2Test() {
        List<Item> itemList = itemRepository.getItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈2-3")
    public void quiz2_3Test() {
        List<Item> itemList = itemRepository.getId(7L);

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("native query")
    public void findByItemDetailByNativeTest() {
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세");

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @PersistenceContext
    EntityManager em; //엔티티 매니저 객체 (쿼리문 실행)

    @Test
    @DisplayName("querydsl 조회 테스트")
    public void queryDslTest() {
        JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리문 동적으로 실행
        QItem qItem = QItem.item; //Item 엔티티 객체

        /*
        * 실행 쿼리문:
          select * from item
          where item_sell_status - 'SELL'
          and item_detail like '%테스트 상품 상세%'
          order by price desc;
        */

        //쿼리문을 실행했을 때 결과값을 담을 엔티티 타입을 제네릭에 선언
        JPAQuery<Item> query = qf.selectFrom(qItem) //select * from item
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL)) //where item_sell_status - 'SELL'
                .where(qItem.itemDetail.like("%테스트 상품 상세%")) //and item_detail like '%테스트 상품 상세%'
                .orderBy(qItem.price.desc()); //order by price desc;

        List<Item> itemList = query.fetch(); //쿼리문 실행

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈3-1")
    public void quiz3_1Test() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        JPAQuery<Item> query = qf.selectFrom(qItem)
                .where(qItem.itemNm.eq("테스트 상품1"))
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈3-2")
    public void quiz3_2Test() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        JPAQuery<Item> query = qf.selectFrom(qItem)
                .where(qItem.price.between(10004, 10008));

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈3-3")
    public void quiz3_3Test() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        LocalDateTime localDateTime
                = LocalDateTime.of(2023, 1, 1, 12, 12, 44);

        JPAQuery<Item> query = qf.selectFrom(qItem)
                .where(qItem.regTime.after(localDateTime));

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈3-4")
    public void quiz3_4Test() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        JPAQuery query = qf.selectFrom(qItem)
                .where(qItem.itemSellStatus.isNotNull());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("퀴즈3-5")
    public void quiz3_5Test() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        JPAQuery query = qf.selectFrom(qItem)
                .where(qItem.itemDetail.like("%설명1"));

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("querydsl 조회 테스트2")
    public void queryDslTest2() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        /*
        select * from item
        where item_sell_status = 'SELL'
        and item_detail like '%테스트 상품 상세%'
        and price > 10003;
        */

        //페이징 (객체)
        // - Pageable은 0부터 시작되도록 설계
        Pageable page = PageRequest.of(0, 4); //of(시작페이지 번호 (주의: 0부터 시작), 한 페이지당 조회할 레코드의 개수)

        JPAQuery<Item> query = qf.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%테스트 상품 상세%"))
                .where(qItem.price.gt(10003))
                .offset(page.getOffset()) //페이징 처리 시 쿼리문 뒤에 해당 두줄 작성 필요
                .limit(page.getPageSize());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    }


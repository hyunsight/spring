package com.example.myLibrary.controller;

import com.example.myLibrary.dto.Book;
import com.example.myLibrary.service.BookService;
import com.example.myLibrary.util.PagingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    PagingUtil pagingUtil;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/list",
            method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "categoryId", required = false) Integer categoryId, HttpSession session, HttpServletRequest request, Model model) {
        try {
            String pageNum = request.getParameter("pageNum");
            pagingUtil.setCurrentPage(1);

            if (pageNum != null) pagingUtil.setCurrentPage(Integer.parseInt(pageNum));

            Integer id = (Integer) session.getAttribute("id");
            if (id == null) {
                return "redirect:/login";
            }

            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            if (searchValue == null) {
                //검색어가 없다면
                searchKey = "book_name";
                searchValue = "";
            } else {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            Map map = new HashMap();
            map.put("id", id);
            map.put("searchKey", searchKey);
            map.put("searchValue", searchValue);
            if (categoryId != null) {
                map.put("categoryId", categoryId);
            }

            int dataCount = bookService.getDataCount(map);
            pagingUtil.resetPaging(dataCount, 10);

            map.put("start", pagingUtil.getStart());
            map.put("end", pagingUtil.getEnd());

            List<Book> lists = bookService.getBookList(map);

            String param = "";
            String listUrl = "/list";
            String articleUrl = "/view?pageNum=" + pagingUtil.getCurrentPage();

            if (searchValue != null && !searchValue.equals("")) {
                param = "searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            if (!param.equals("")) {
                listUrl += "?" + param;
                articleUrl += "&" + param;
            }

            String pageIndexList = pagingUtil.pageIndexList(listUrl);

            model.addAttribute("lists", lists); //DB에서 가져온 전체 게시물리스트
            model.addAttribute("articleUrl", articleUrl); //상세페이지로 이동하기 위한 url
            model.addAttribute("pageIndexList", pageIndexList); //페이징 버튼
            model.addAttribute("dataCount", dataCount); //게시물의 전체 개수
            model.addAttribute("searchKey", searchKey); //검색키워드
            model.addAttribute("searchValue", searchValue); //검색어

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "book/list";
    }

    @GetMapping(value = "/view")
    public String view(HttpServletRequest request, Model model) {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            if (searchValue != null) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            Book book = bookService.getBookRead(bookId);

            if (book == null) return "redirect:/list?pageNum=" + pageNum;

            String param = "pageNum=" + pageNum;

            if (searchValue != null && !searchValue.equals("")) {
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            model.addAttribute("book", book);
            model.addAttribute("params", param);
            model.addAttribute("pageNum", pageNum);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "book/view";
    }

    @GetMapping(value = "/write")
    public String write() {
        return "book/write";
    }

    @PostMapping(value = "/insert")
    public String inserBook(Book book, HttpSession session) {
        try {
            Object id = session.getAttribute("id");

            if (id == null) {
                return "redirect:/login";
            } else {
                book.setId((int)id);
                bookService.insertBook(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/list";
    }


    @GetMapping(value = "/rewrite")
    public String rewrite(HttpServletRequest request, Model model) {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            Book book = bookService.getBookRead(bookId);
            if (book == null) return "redirect:/list?pageNum=" + pageNum;

            String param = "pageNum=" + pageNum;

            if(searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            model.addAttribute("book", book);
            model.addAttribute("params", param);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("searchKey", searchKey);
            model.addAttribute("searchValue", searchValue);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        return "book/rewrite";
    }


    @PostMapping(value = "/update")
    public String update(Book book, HttpSession session, HttpServletRequest request) {
        String param = "";

        try {
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");
            param = "bookId=" + book.getBookId() + "&pageNum=" + pageNum;

            if(searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            Object id = session.getAttribute("id");

            if (id == null) {
                return "redirect:/login"; //세션 만료 시, 로그인 페이지로 이동
            } else {
                bookService.updateBook(book); //포스트 update 서비스 호출
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/view?" + param;
    }


    @DeleteMapping(value = "/delete/{bookId}")
    public @ResponseBody ResponseEntity deleteBook(@PathVariable("bookId") int bookId, HttpSession session) {
        try {
            Object id = session.getAttribute("id");

            if(id == null) {
                return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.UNAUTHORIZED);

            } else {
                bookService.deleteBook(bookId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("삭제 실패. 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
        }

        //ResponseEntity<첫번째 매개변수의 타입>(result 결과, response 상태코드)
        return new ResponseEntity<Integer>(bookId, HttpStatus.OK);
    }

    }

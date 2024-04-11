package com.example.myLibrary.controller;

import com.example.myLibrary.dto.Book;
import com.example.myLibrary.service.BookService;
import com.example.myLibrary.util.PagingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

}

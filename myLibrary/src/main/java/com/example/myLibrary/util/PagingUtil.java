package com.example.myLibrary.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class PagingUtil {
    private int dataCount; //총 게시물의 갯수
    private int numPerPage; //페이지당 보여줄 게시물의 개수
    private int totalPage; //페이지의 전체 갯수
    private int currentPage = 1; //현재 페이지(디폴트는 첫번째 페이지를 보여주므로 1)
    private int start; //rownum의 시작값
    private int end; //rownum의 끝값

    public void resetPaging(int dataCount, int numPerPage) {
        this.dataCount = dataCount;
        this.numPerPage = numPerPage;

        this.totalPage = getPageCount();

        if(this.currentPage > this.totalPage) this.currentPage = this.totalPage;
        this.start = (this.currentPage - 1) * numPerPage + 1;
        this.end = this.currentPage * numPerPage;
    }

    public int getPageCount() {
        int pageCount = 0;
        pageCount = dataCount / numPerPage;

        if(dataCount % numPerPage != 0) pageCount++;
        return pageCount;
    }

    public String pageIndexList(String listUrl) {
        StringBuffer sb = new StringBuffer();

        int numPerBlock = 5;
        int currentPageSetup;
        int page;

        if(currentPage == 0 || totalPage == 0) return "";

        if(listUrl.indexOf("?") != -1) {
            listUrl += "&";
        } else {
            listUrl += "?";
        }

        currentPageSetup = (currentPage / numPerBlock) * numPerBlock;

        if(currentPage % numPerBlock == 0) {
            currentPageSetup = currentPageSetup - numPerBlock;
        }

        if(totalPage > numPerBlock && currentPageSetup > 0) {
            sb.append("<li class=\"page-item\">" +
                    "       <a class=\"page-link\" href=\"" + listUrl + "pageNum=" + currentPageSetup + "\" aria-label=\"Previous\">" +
                    "            <span aria-hidden=\"true\">이전</span>" +
                    "       </a>" +
                    " </li>");
        }

        page = currentPageSetup + 1;

        while (page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
            if(page == currentPage) {
                sb.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a></li>");
            } else {
                sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a></li>");
            }

            page++;
        }

        if(totalPage - currentPageSetup > numPerBlock) {
            sb.append("<li class=\"page-item\">" +
                    "     <a class=\"page-link\" href=\"" + listUrl + "pageNum=" + (currentPageSetup + numPerBlock + 1) + "\" aria-label=\"Next\">" +
                    "         <span aria-hidden=\"true\">다음</span>" +
                    "     </a>" +
                    " </li>");
        }

        return sb.toString();
    }
}

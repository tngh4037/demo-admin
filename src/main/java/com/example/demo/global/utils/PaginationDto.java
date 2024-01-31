package com.example.demo.global.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// TODO :: 구조 개선 고민할 것
@Getter
@Setter
@ToString
public class PaginationDto {

    // 현재 페이지 번호
    private int pageNo;
    // 페이지 당 출력할 데이터 개수
    private int recordCount;
    // 페이지 목록에 노출할 페이지 개수
    private int pageSize;

    // 전체 데이터 개수
    private int totalRecordCount;
    // 전체 페이지 개수
    private int totalPageCount;
    // 현재 페이지 목록의 첫 페이지 번호
    private int firstPage;
    // 현재 페이지 목록의 마지막 페이지 번호
    private int lastPage;
    // LIMIT 시작 위치
    private int startNo;
    // 이전 페이지 존재 여부
    private boolean hasPreviousPage;
    // 다음 페이지 존재 여부
    private boolean hasNextPage;

    public PaginationDto(int pageNo, int recordCount, int pageSize) {
        this.pageNo = pageNo;
        this.recordCount = recordCount;
        this.pageSize = pageSize;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
        if (totalRecordCount > 0) {
            calculate();
        }
    }

    private void calculate() {

        // 전체 페이지 수 ( 현재 페이지 번호가 전체 페이지 수보다 크면, 현재 페이지 번호에 전체 페이지 수를 저장 )
        totalPageCount = ((totalRecordCount - 1) / this.getRecordCount()) + 1;
        if (this.getPageNo() > totalPageCount) {
            this.setPageNo(totalPageCount);
        }

        // 현재 페이지 목록의 첫 페이지 번호
        firstPage = ((this.getPageNo() - 1) / this.getPageSize()) * this.getPageSize() + 1;

        // 현재 페이지 목록의 마지막 페이지 번호 ( 현재 페이지 목록의 마지막 페이지 번호가 전체 페이지 수보다 큰 경우, 마지막 페이지 번호에 전체 페이지 수 저장 )
        lastPage = firstPage + this.getPageSize() - 1;
        if (lastPage > totalPageCount) {
            lastPage = totalPageCount;
        }

        // LIMIT 시작 위치
        startNo = (this.getPageNo() - 1) * this.getRecordCount();

        // 이전 페이지 존재 여부
        hasPreviousPage = firstPage > 0 && firstPage != 1;
        if (!hasPreviousPage) {
            hasPreviousPage = pageNo != firstPage;
        }

        // 다음 페이지 존재 여부
        hasNextPage = (lastPage * this.getRecordCount()) < totalRecordCount;
        if (!hasNextPage) {
            hasNextPage = pageNo != lastPage;
        }
    }
}
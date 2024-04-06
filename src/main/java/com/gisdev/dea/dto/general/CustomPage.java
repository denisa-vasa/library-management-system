package com.gisdev.dea.dto.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CustomPage<T> {

    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> data;

    public CustomPage(Page<T> page) {
        data = page.getContent();
        currentPage = page.getNumber();
        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
    }

    public CustomPage(int pageNumber, int pageSize, List<T> data) {
        this(new Pagination(pageNumber, pageSize, data));
    }

    private CustomPage(Pagination<T> pagination) {
        currentPage = pagination.getPageNumber();
        totalPages = pagination.getTotalPages();
        totalElements = pagination.getTotalResults();
        data = pagination.getData();
    }

    @Getter
    @Setter
    private static class Pagination<T> {

        private int pageNumber;
        private int pageSize;
        private long totalResults;
        private List<T> data;
        private int totalPages;

        public Pagination(int pageNumber, int pageSize, List<T> data) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.data = data;
            this.totalResults = data.size();
            initData();
        }

        private void initData() {
            calculatePage();
            int skipCount = pageNumber * pageSize;
            data = data.parallelStream().skip(skipCount).limit(pageSize).collect(Collectors.toList());
        }

        private void calculatePage() {

            if (pageNumber < 0) {
                throw new IllegalArgumentException("Page number can't be negative: " + pageNumber);
            }

            if (pageSize > 0) {
                if (data.size() % pageSize == 0) {
                    totalPages = data.size() / pageSize;
                } else {
                    totalPages = (data.size() / pageSize) + 1;
                }
            }
        }
    }

    public static <T> CustomPage<T> empty() {
        CustomPage<T> customPage = new CustomPage<>();
        customPage.setCurrentPage(0);
        customPage.setTotalElements(0);
        customPage.setTotalPages(0);
        customPage.setData(Collections.EMPTY_LIST);

        return customPage;
    }

    public static <T> CustomPage<T> of(int currentPage, int totalPages, long totalElements, List<T> data) {
        CustomPage<T> customPage = new CustomPage<>();
        customPage.setCurrentPage(currentPage);
        customPage.setTotalElements(totalElements);
        customPage.setTotalPages(totalPages);
        customPage.setData(data);

        return customPage;
    }
}
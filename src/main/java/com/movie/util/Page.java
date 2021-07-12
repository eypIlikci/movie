package com.movie.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Page<T> {
   public int pageNumber;
   public List<Integer> pages;
   public List<T> content;
   public long totalElements;

    public Page(int pageNumber, int totalPage, long totalElements,List<T> content) {
        this.pageNumber = pageNumber;
        this.content = content;
        this.totalElements=totalElements;
        this.pages=new ArrayList<Integer>();
        for (int i = 0; i <totalPage ; i++) {
            this.pages.add(i+1);
        }
    }
}

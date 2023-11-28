package com.sima.intranet.Dto;

import com.sima.intranet.Filtro.Filtro;

public class Paginable{
    public Integer page;
    public Integer size;
    public Long totalPages;

    public Paginable(){}

    public Paginable(Integer page, Integer size, Long totalPages) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }

    public Paginable(Filtro filtro) {
        this.page = filtro.getPage();
        this.size = filtro.getSize();
        this.totalPages = filtro.getTotalPages();
    }
}

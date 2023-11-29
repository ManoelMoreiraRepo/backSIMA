package com.sima.intranet.Filtro;

import com.sima.intranet.Entity.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
public abstract class Filtro {
    private Integer page = 0;
    private Integer size = 20;

    private long totalPages = 0;

    public Filtro(){}

    public Integer getPage(){
        return this.page;
    };

    public Integer getSize(){
        return this.size;
    };

    public Long getTotalPages(){
        return this.totalPages;
    }


    public void setTotalPages(Long i) {
        this.totalPages = i/this.size;
    }

    public abstract  <T> Predicate[] getPredicates(CriteriaQuery<T> query , CriteriaBuilder builder , List<From> from );
}
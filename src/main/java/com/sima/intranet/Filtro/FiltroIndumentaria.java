package com.sima.intranet.Filtro;

import com.sima.intranet.Enumarable.Gerencia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;

import java.util.List;

@Data
public class FiltroIndumentaria extends Filtro{
    private List<Integer> anios;
    private List<Gerencia> gerencias;
    @Override
    public <T> Predicate[] getPredicates(CriteriaQuery<T> query, CriteriaBuilder builder, List<From> from) {
        return new Predicate[0];
    }
}

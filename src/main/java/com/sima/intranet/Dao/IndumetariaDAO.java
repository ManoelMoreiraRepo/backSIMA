package com.sima.intranet.Dao;

import com.sima.intranet.Diccionario.Empleado_;
import com.sima.intranet.Diccionario.Indumentaria_;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Entity.Indumentaria;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Filtro.FiltroIndumentaria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndumetariaDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Object[]> obtenerDatosPorAñoYGerencia(FiltroIndumentaria filtro) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Indumentaria> indumentariaRoot = cq.from(Indumentaria.class);
        Join<Indumentaria, Empleado> empleadoJoin = indumentariaRoot.join(Indumentaria_.EMPLEADO , JoinType.INNER);

        cq.multiselect(
                empleadoJoin.get(Empleado_.GERENCIA),
                cb.function("YEAR", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 1), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 2), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 3), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 4), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 5), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 6), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 7), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 8), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 9), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 10), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 11), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(
                        (cb.selectCase()
                                .when(cb.equal(cb.function("MONTH", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)), 12), indumentariaRoot.get(Indumentaria_.CANTIDAD))
                                .otherwise(0)).as(BigInteger.class)
                ),
                cb.sum(indumentariaRoot.get(Indumentaria_.CANTIDAD)) //TOTAL
        );

        List<Predicate> cond = new ArrayList<>();
        if(filtro.getAnios() != null && !filtro.getAnios().isEmpty()){
            cond.add(cb.function("YEAR", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)).in(filtro.getAnios()));
        }
        if(filtro.getGerencias() != null && !filtro.getGerencias().isEmpty()){
            cond.add(empleadoJoin.get(Empleado_.GERENCIA).in(filtro.getGerencias()));
        }
        cq.where(
                cond.toArray(new Predicate[]{})
        );

        cq.groupBy(empleadoJoin.get(Empleado_.GERENCIA), cb.function("YEAR", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA)));
        cq.orderBy(cb.asc(empleadoJoin.get(Empleado_.GERENCIA)), cb.desc(cb.function("YEAR", Integer.class, indumentariaRoot.get(Indumentaria_.FECHA_ENTREGA))));

        return entityManager.createQuery(cq).getResultList();
    }
}

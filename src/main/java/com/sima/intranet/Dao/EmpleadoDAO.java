package com.sima.intranet.Dao;

import com.sima.intranet.Diccionario.Empleado_;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Filtro.FiltroEmpleado;
import com.sima.intranet.Util.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmpleadoDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Long getCantidadPorEmpresa(List<Gerencia> gerencias){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Empleado> root = query.from(Empleado.class);
        List<Predicate> cond = new ArrayList<>();

        cond.add(root.get(Empleado_.GERENCIA).in(gerencias));

        query.where(cond.toArray(new Predicate[0]));
        Selection<Long> count = builder.count(root.get(Empleado_.ID));
        query.select(count);
        return entityManager.createQuery(query).getSingleResult();
    }

    public List<Empleado> getListadoEmpleado(FiltroEmpleado filtro) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empleado> query = builder.createQuery(Empleado.class);
        Root<Empleado> root = query.from(Empleado.class);
        List<From> from = new ArrayList<>();
        from.add(root);
        query.where(filtro.getPredicates(query , builder , from));
        filtro.setTotalPages(getCountListadoEmpleado(filtro));
        return entityManager.createQuery(query).setFirstResult(filtro.getPage()*filtro.getSize()).setMaxResults(filtro.getSize()).getResultList();
    }

    public Long getCountListadoEmpleado(FiltroEmpleado filtro){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Empleado> root = query.from(Empleado.class);
        List<From> from = new ArrayList<>();
        from.add(root);
        query.where(filtro.getPredicates(query , builder , from));
        query.select(builder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }
}

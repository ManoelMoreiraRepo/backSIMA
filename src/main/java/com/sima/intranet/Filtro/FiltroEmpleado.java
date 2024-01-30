package com.sima.intranet.Filtro;

import com.sima.intranet.Diccionario.Empleado_;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Util.Strings;
import jakarta.persistence.criteria.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FiltroEmpleado extends Filtro {
    private String nombreEmpleado;
    private String ordenado;
    private String orden;
    private String gerencia;
    private String objetivo;

    public <T> Predicate[] getPredicates(CriteriaQuery<T> query , CriteriaBuilder builder , List<From> from ){
        List<Predicate> cond = new ArrayList<>();
        Root<Empleado> root = (Root<Empleado>) from.get(0);

        if(Strings.isNotNullOrEmpty(this.getOrdenado())){
            if(this.getOrden().equals("ASC")){
                query.orderBy(builder.asc(root.get(this.getOrdenado())));
            }else{
                query.orderBy(builder.desc(root.get(this.getOrdenado())));
            }
        }

        if(Strings.isNotNullOrEmpty(this.getNombreEmpleado())){
            String[] arrayEmpl = this.getNombreEmpleado().split(" ");
            for(String item : arrayEmpl){
                String dato = "%"+item+"%";
                cond.add(builder.or(
                        builder.like(root.get(Empleado_.DNI) , dato),
                        builder.like(root.get(Empleado_.NOMBRE) , dato),
                        builder.like(root.get(Empleado_.APELLIDO) , dato)
                ));
            }
        }

        if(Strings.isNotNullOrEmpty(this.getGerencia())){
            cond.add(builder.equal(root.get(Empleado_.GERENCIA) , Gerencia.valueOf(this.getGerencia())));
        }

        if(Strings.isNotNullOrEmpty(this.getObjetivo())){
            cond.add(builder.like(root.get(Empleado_.OBJETIVO) , "%"+this.getObjetivo()+"%"));
        }

        return cond.toArray(new Predicate[0]);
    }
}

package com.sima.intranet.Service;

import com.sima.intranet.Entity.Infraccion;
import com.sima.intranet.Enumarable.EstadoInfraccion;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Interface.InfraccionInterface;
import com.sima.intranet.Repository.InfraccionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class InfraccionServicio implements InfraccionInterface {
    @Autowired
    private InfraccionRepository infraccionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Infraccion> buscarPorNumeroActa(String numero) {
        return infraccionRepository.findByNumero(numero);
    }

    @Override
    public void save(Infraccion infraccion) {
        infraccionRepository.save(infraccion);
    }

    @Override
    public void saveAll(List<Infraccion> infraccionList) {
        infraccionRepository.saveAll(infraccionList);
    }
    @Override
    public Map getEstadisticas(){
        List<String> gerencia = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
        List<BigDecimal> totales = new ArrayList<>();
        List<Integer> sinAsignar = new ArrayList<>();
        List<Integer> asignada = new ArrayList<>();
        List<Integer> cobrada = new ArrayList<>();
        List<Integer> pendiente = new ArrayList<>();


        for(Gerencia g : Gerencia.values()){
            List<Infraccion> infracciones = infraccionRepository.getInfraccionesPorGerencia(g);
            gerencia.add(g.descrip);
            cantidades.add(infracciones.size());
            BigDecimal importeTotal = BigDecimal.ZERO;
            Integer sin = 0;
            Integer con = 0;
            Integer pagada = 0;
            Integer noPagada = 0;
            for(Infraccion i : infracciones){
                importeTotal.add(i.getImporte());
                if(i.getAsignado()!=null && !i.getAsignado().isEmpty()){
                    con++;
                }else {
                    sin++;
                }

                if(i.getEstado() == null || i.getEstado().equals(EstadoInfraccion.ADEUDADA)){
                    noPagada++;
                }else{
                    pagada++;
                }

            }
            totales.add(importeTotal);
            sinAsignar.add(sin);
            asignada.add(con);
            cobrada.add(pagada);
            pendiente.add(noPagada);
        }

        return Map.of("gerencia" , gerencia ,
                "cantidades" , cantidades ,
                "totales" , totales  ,
                "sinAsignar" , sinAsignar ,
                "asignada" , asignada ,
                "cobrada" , cobrada ,
                "pendiente" , pendiente );



    }


}

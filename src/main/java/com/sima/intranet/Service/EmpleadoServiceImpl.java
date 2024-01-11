package com.sima.intranet.Service;

import com.sima.intranet.Dao.EmpleadoDAO;
import com.sima.intranet.Diccionario.Empleado_;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Empresa;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Sindicato;
import com.sima.intranet.Filtro.FiltroEmpleado;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Repository.EmpleadoRepository;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoInterface {

    @Autowired
    EmpleadoRepository iEmpleadoRepository;

    @Autowired
    EmpleadoDAO empleadoDAO;

    @Override
    public Page<Empleado> getEmpleados(Pageable pageable, Gerencia gerencia) {
        if(gerencia == null){
            return iEmpleadoRepository.findAll(pageable);
        }
        return iEmpleadoRepository.findByGerencia( pageable , gerencia);
    }
    
    @Override
    public Page<Empleado> findEmpleado(Pageable pageable, String nombreEmpleado, Gerencia gerencia) {
        if(gerencia == null){
            return iEmpleadoRepository.findByPorNombreyApellidoDNI(pageable , nombreEmpleado);
        }
        return iEmpleadoRepository.findByPorNombreyApellidoDNIGerencia(pageable , nombreEmpleado , gerencia);
    }
    
    @Override
    public void saveEmpleado(Empleado newEmpleado) {
        iEmpleadoRepository.save(newEmpleado);
    }

    @Override
    public void deleteEmpleado(Long idEmpleado) {
        iEmpleadoRepository.deleteById(idEmpleado);
    }

    @Override
    public void modifyEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoEncontrado = this.iEmpleadoRepository.findById(empleado.getIdEmpleado());
        if (empleadoEncontrado.get() != null) {
            empleadoEncontrado.get().setEstadoEmpleado(empleado.getEstadoEmpleado());
            empleadoEncontrado.get().setNombreEmpleado(empleado.getNombreEmpleado());
            empleadoEncontrado.get().setApellidoEmpleado(empleado.getApellidoEmpleado());
            empleadoEncontrado.get().setDNIEmpleado(empleado.getDNIEmpleado());
            empleadoEncontrado.get().setLegajoEmpleado(empleado.getLegajoEmpleado());
            empleadoEncontrado.get().setFechaNascimentoEmpleado(empleado.getFechaNascimentoEmpleado());
            empleadoEncontrado.get().setFechaAltaEmpleado(empleado.getFechaAltaEmpleado());
            empleadoEncontrado.get().setObjetivoEmpleado(empleado.getObjetivoEmpleado());
            empleadoEncontrado.get().setTurnoEmpleado(empleado.getTurnoEmpleado());
            empleadoEncontrado.get().setTelefonoEmpleado(empleado.getTelefonoEmpleado());
            empleadoEncontrado.get().setCargoEmpleado(empleado.getCargoEmpleado());
            empleadoEncontrado.get().setDireccionEmpleado(empleado.getDireccionEmpleado());
            empleadoEncontrado.get().setEmailEmpleado(empleado.getEmailEmpleado());
            empleadoEncontrado.get().setCodigoPostalEmpleado(empleado.getCodigoPostalEmpleado());
            iEmpleadoRepository.save(empleado);
        }
    }

    @Override
    public Optional<Empleado> findByLegajo(String legajo) {
        return iEmpleadoRepository.findByLegajoEmpleado(legajo);
    }

    @Override
    public Optional<Empleado> findByDNI(String dni) {
        return iEmpleadoRepository.findByDNIEmpleado(dni);
    }

    @Override
    public void saveAll(List<Empleado> lista) {
        iEmpleadoRepository.saveAll(lista);
    }


    @Override
    public Empleado findEmpleado(Long id) {
    Empleado empleado = iEmpleadoRepository.findById(id).get();
        return empleado;    
    }
    @Override
    public List<Map<String,Object>> getCantidadNominaPorGerencia() {
        List<String[]> resultado = iEmpleadoRepository.countEmpleadoByGerencia();
        List<Map<String,Object>> lista = new ArrayList<>();

        // Crear un conjunto para realizar un seguimiento de las gerencias presentes
        Set<Gerencia> gerenciasPresentes = new HashSet<>();
        for (String[] dato : resultado) {
            Gerencia gerencia = null;
            String ger = Optional.ofNullable(dato[1]).orElse("");
            if(!ger.isEmpty()){
                gerencia = Gerencia.valueOf(ger);
            }
            if (gerencia != null) {
                gerenciasPresentes.add(gerencia);
            }
        }

        // Iterar sobre los valores del enumerador Gerencia
        for (Gerencia g : Gerencia.values()) {
            if (!gerenciasPresentes.contains(g)) {
                // Agregar un registro con cantidad 0 para gerencias que no están en la consulta
                String descripcion = String.format("%s   NOMINA ACTIVA", g.codigo);
                String titulo = g.descrip;
                lista.add(Map.of("cant", 0, "titulo", titulo, "descrip", descripcion , "url" , "/rrhh/empleado" , "ger" , g.name()));
            }
        }

        // Iterar sobre los resultados de la consulta y agregar los registros correspondientes
        for (String[] dato : resultado) {
            Gerencia gerencia = null;
            String ger = Optional.ofNullable(dato[1]).orElse("");
            if(!ger.isEmpty()){
                gerencia = Gerencia.valueOf(ger);
            }
            if (gerencia != null) {
                String descripcion = String.format("%s   NOMINA ACTIVA", gerencia.codigo);
                String titulo = gerencia.descrip;
                lista.add(Map.of("cant", dato[0], "titulo", titulo, "descrip", descripcion ,"url" , "/rrhh/empleado" , "ger" , gerencia.name()));
            }
        }

        return lista;
    }

    @Override
    public List<Map<String,Object>> getCantidadNominaPorSindicado() {
        List<String[]> resultado = iEmpleadoRepository.countEmpleadoBySindicato();
        List<Map<String,Object>> lista = new ArrayList<>();

        // Crear un conjunto para realizar un seguimiento de los sindicatos presentes
        Set<Sindicato> sindicatosPresentes = new HashSet<>();
        for (String[] dato : resultado) {
            Sindicato sindicato = Sindicato.getSindicato(Optional.ofNullable(dato[1]).orElse(""));
            if (sindicato != null) {
                sindicatosPresentes.add(sindicato);
            }
        }

        // Iterar sobre los valores del enumerador Sindicato
        for (Sindicato s : Sindicato.values()) {
            if (!sindicatosPresentes.contains(s)) {
                // Agregar un registro con cantidad 0 para sindicatos que no están en la consulta
                String descripcion = "NOMINA ACTIVA";
                String titulo = s.titulo;
                lista.add(Map.of("cant", 0, "titulo", titulo, "descrip", descripcion));
            }
        }

        // Iterar sobre los resultados de la consulta y agregar los registros correspondientes
        for (String[] dato : resultado) {
            Sindicato sindicato = Sindicato.getSindicato(Optional.ofNullable(dato[1]).orElse(""));
            String descripcion = "NOMINA ACTIVA";
            if (sindicato != null) {
                String titulo = sindicato.titulo;
                lista.add(Map.of("cant", dato[0], "titulo", titulo, "descrip", descripcion));
            }

        }

        return lista;
    }

    @Override
    public List<Map<String,Object>> getCountByEmpresa() {
        List<Map<String,Object>> lista = new ArrayList<>();

        for(Empresa empresa : Empresa.values()){
            /*if(empresa.equals(Empresa.SIN_EMPRESA)){
                continue;
            }*/
            Long result = empleadoDAO.getCantidadPorEmpresa(empresa);
           /* List<String> descrip = empresa.gerencias.stream().map(Gerencia::getCodigo).collect(Collectors.toList());
            String descripcion = String.join(" / " , descrip);
            if(descripcion.equals("")){
                descripcion= "SIN GERENCIAS";
            }*/
            lista.add(Map.of("cant", result , "titulo", empresa.descripcion , "descrip", ""));
        }

        return lista;
    }

    @Override
    public List<Empleado> findEmpleado(FiltroEmpleado filtro) {
        return empleadoDAO.getListadoEmpleado(filtro);
    }



}

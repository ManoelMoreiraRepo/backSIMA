package com.sima.intranet.Service;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Sindicato;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Repository.EmpleadoRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoInterface {

    @Autowired
    EmpleadoRepository iEmpleadoRepository;

    @Override
    public List<Empleado> getEmpleados() {
        List<Empleado> empleadosmostrar = iEmpleadoRepository.findAll();
        return empleadosmostrar;
    }
    
    @Override
    public List<Empleado> findEmpleado(String nombreEmpleado) {
        return iEmpleadoRepository.findByNombreEmpleadoContainingIgnoreCase(nombreEmpleado);
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
            Gerencia gerencia = Gerencia.valueOf(Optional.ofNullable(dato[1]).orElse(""));
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
                lista.add(Map.of("cant", 0, "titulo", titulo, "descrip", descripcion));
            }
        }

        // Iterar sobre los resultados de la consulta y agregar los registros correspondientes
        for (String[] dato : resultado) {
            Gerencia gerencia = Gerencia.valueOf(Optional.ofNullable(dato[1]).orElse(""));
            if (gerencia != null) {
                String descripcion = String.format("%s   NOMINA ACTIVA", gerencia.codigo);
                String titulo = gerencia.descrip;
                lista.add(Map.of("cant", dato[0], "titulo", titulo, "descrip", descripcion));
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
                String titulo = s.name();
                lista.add(Map.of("cant", 0, "titulo", titulo, "descrip", descripcion));
            }
        }

        // Iterar sobre los resultados de la consulta y agregar los registros correspondientes
        for (String[] dato : resultado) {
            Sindicato sindicato = Sindicato.getSindicato(Optional.ofNullable(dato[1]).orElse(""));
            String descripcion = "NOMINA ACTIVA";
            if (sindicato != null) {
                String titulo = sindicato.name();
                lista.add(Map.of("cant", dato[0], "titulo", titulo, "descrip", descripcion));
            }

        }

        return lista;
    }


}

package com.sima.intranet.Service;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Empresas;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Repository.EmpleadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void saveAll(List<Empleado> lista) {
        iEmpleadoRepository.saveAll(lista);
    }


    @Override
    public Empleado findEmpleado(Long id) {
    Empleado empleado = iEmpleadoRepository.findById(id).get();
        return empleado;    
    }
    @Override
    public List<Map<String,Object>> getCantidadNominaPorEmpresa(){
       List<String[]> resultado = iEmpleadoRepository.countEmpleadoByTipoEmpresa();
       List<Map<String,Object>> lista = new ArrayList<>();
        for(String[]  dato  : resultado.stream().toList()){
            lista.add(Map.of("cant" , dato[0] , "titulo" ,Empresas.valueOf(Optional.ofNullable(dato[1]).orElse("SIN_EMPRESA")).descripcion , "descrip" ,"NOMINA ACTIVA"));
        }
       return lista;
    }

}

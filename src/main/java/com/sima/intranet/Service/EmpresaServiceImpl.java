package com.sima.intranet.Service;

import com.sima.intranet.Entity.Empresa;
import com.sima.intranet.Interface.EmpresaInterface;
import com.sima.intranet.Repository.EmpresaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements EmpresaInterface {
    
    @Autowired
    private EmpresaRepository rempresa;
    
    @Override
    public Empresa newEmpresa(Empresa newEmpresa) {
        return rempresa.save(newEmpresa);
    }
    
    @Override
    public Iterable<Empresa> getAll() {
        return this.rempresa.findAll();
    }
    
    @Override
    public Empresa modifyEmpresa(Empresa empresa) {
        Optional<Empresa> empresaEncontrada = this.rempresa.findById(empresa.getIdEmpresa());
        if (empresaEncontrada.get() != null) {
            empresaEncontrada.get().setNombreEmpresa(empresa.getNombreEmpresa());
            empresaEncontrada.get().setDireccionEmpresa(empresa.getDireccionEmpresa());
            empresaEncontrada.get().setCIFEmpresa(empresa.getCIFEmpresa());
            empresaEncontrada.get().setGrupoEmpresa(empresa.getGrupoEmpresa());
        }
        return null;
    }
    
    @Override
    public Boolean deleteEmpresa(Long idEmpresa) {
        this.rempresa.deleteById(idEmpresa);
        return true;        
    }
    
}

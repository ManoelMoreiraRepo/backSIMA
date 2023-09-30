package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Empresa;

public interface EmpresaInterface {
    
    Empresa newEmpresa(Empresa newEmpresa);
    
    Iterable<Empresa> getAll();
    
    Empresa modifyEmpresa(Empresa empresa);
    
    Boolean deleteEmpresa(Long idEmpresa);
}

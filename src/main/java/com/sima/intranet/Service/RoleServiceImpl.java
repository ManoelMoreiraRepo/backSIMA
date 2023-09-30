package com.sima.intranet.Service;

import com.sima.intranet.Entity.Role;
import com.sima.intranet.Interface.RoleInterface;
import com.sima.intranet.Repository.RoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleInterface{
    @Autowired
    private RoleRepository rrole;

    @Override
    public Role newRole(Role newRole) {
    return rrole.save(newRole);
    }

    @Override
    public Iterable<Role> gettAll() {
    return this.rrole.findAll();
    }

    @Override
    public Role modyfyRole(Role role) {
        Optional<Role> roleEncontrado =this.rrole.findById(role.getIdRole());
        
        if(roleEncontrado.get() != null){
        roleEncontrado.get().setNombreRole(role.getNombreRole());
        this.newRole(roleEncontrado.get());
                }
        return null;
    }

    @Override
    public Boolean deleteRole(int idRole) {
    this.rrole.deleteById(idRole);
    return true;
    }
    
}

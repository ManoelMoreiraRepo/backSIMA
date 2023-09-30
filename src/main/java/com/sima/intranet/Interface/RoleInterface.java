package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Role;

public interface RoleInterface {
    
    Role newRole(Role newRole);
    
    Iterable<Role> gettAll();
    
    Role modyfyRole(Role role);
    
    Boolean deleteRole(int idRole);
}

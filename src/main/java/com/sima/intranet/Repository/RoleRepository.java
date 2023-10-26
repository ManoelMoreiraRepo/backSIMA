package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Role;
import com.sima.intranet.Enumarable.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByName(ERole name);
}

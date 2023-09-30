
package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}

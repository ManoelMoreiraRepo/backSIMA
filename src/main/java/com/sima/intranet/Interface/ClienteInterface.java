package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Cliente;

public interface ClienteInterface {

    Cliente newCliente(Cliente newCliente);
    
    Iterable<Cliente> getAll();    

    Cliente modifyCliente(Cliente cliente);
     
    Boolean deleteCliente(Long idCliente);
    
    public Cliente findCliente(Long id);
     
    

}

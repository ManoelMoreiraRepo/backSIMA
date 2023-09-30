package com.sima.intranet.Service;

import com.sima.intranet.Entity.Cliente;
import com.sima.intranet.Interface.ClienteInterface;
import com.sima.intranet.Repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteInterface {

    @Autowired
    ClienteRepository iClienteRepository;

    @Override
    public Iterable<Cliente> getAll() {
        Iterable<Cliente> clientemostrar = iClienteRepository.findAll();
        return clientemostrar;
    }

    @Override
    public Cliente newCliente(Cliente newCliente) {
        return iClienteRepository.save(newCliente);
    }

    @Override
    public Cliente modifyCliente(Cliente cliente) {
        Optional<Cliente> clienteEncontrado = this.iClienteRepository.findById(cliente.getIdCliente());
        if (clienteEncontrado.get() != null) {
            clienteEncontrado.get().setNombreCliente(cliente.getNombreCliente());
            clienteEncontrado.get().setDireccionCliente(cliente.getDireccionCliente());
            clienteEncontrado.get().setObjetivo(cliente.getObjetivo());
        }
        return null;
    }

    @Override
    public Boolean deleteCliente(Long idCliente) {
        iClienteRepository.deleteById(idCliente);
        return true;
    }

    @Override
    public Cliente findCliente(Long id) {
        Cliente cliente = iClienteRepository.findById(id).get();
        return cliente;
    }

}

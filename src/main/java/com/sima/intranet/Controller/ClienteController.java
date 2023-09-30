package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Cliente;
import com.sima.intranet.Entity.Objetivo;
import com.sima.intranet.Interface.ClienteInterface;
import com.sima.intranet.Service.ClienteServiceImpl;
import com.sima.intranet.Service.ObjetivoServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Cliente")
public class ClienteController {

    @Autowired
    ClienteServiceImpl iClienteService;
    @Autowired
    ObjetivoServiceImpl iObjetivoService;

    @GetMapping("/traer")
    public Iterable<Cliente> getCliente() {
        return iClienteService.getAll();
    }

    @PostMapping("/nuevo")
    public String createCliente(@RequestBody Cliente cliente) {
        iClienteService.newCliente(cliente);
        return "El Cliente fue creado correctamente";
    }

    @DeleteMapping("/detail/{id}")
    public void deleteCliente(@PathVariable long id) {
        iClienteService.deleteCliente(id);
    }

    /*@GetMapping("/detail/{id}")
    public Cliente getCliente(@PathVariable long id) {
        return iClienteService.getCliente(id);
    }
*/
    @PutMapping("/actualizar/{id}")
    public String updateCliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
        this.iClienteService.modifyCliente(cliente);
        return "El Cliente fue modificado correctamente";
    }
    
    @PostMapping("/{clienteId}/objetivos")
    public Objetivo createObjetivo(@PathVariable ("clienteId") Long clienteId,@RequestBody Objetivo objetivo){
    Cliente cliente;
    cliente= iClienteService.findCliente(clienteId);
    objetivo.setCliente(cliente);
    return iObjetivoService.newObjetivo(objetivo);
    }
    
    
    
    

}

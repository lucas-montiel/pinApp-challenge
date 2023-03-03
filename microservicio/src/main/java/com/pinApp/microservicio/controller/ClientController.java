package com.pinApp.microservicio.controller;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.pinApp.microservicio.entity.ClientEntity;
import com.pinApp.microservicio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pinApp")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    private Long MAX_YEARS = 75L;


    @GetMapping("/listclientes")
    public List<JsonObject> getClients() {
        List<JsonObject> clientes = new ArrayList<>();
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaNacimiento;
        Long years;
        LocalDate fechaMuerte;
        List<ClientEntity> clientesDB =  clientRepository.findAll();


        for(ClientEntity cliente: clientesDB){
            fechaNacimiento = cliente.getFechaNacimiento();
            years = Long.valueOf((Period.between(fechaNacimiento, fechaHoy).getYears()));
            if( years < MAX_YEARS) {
                  fechaMuerte = fechaNacimiento.plusYears(MAX_YEARS);
            }else {
                fechaMuerte = fechaHoy;
            }
            JsonObject json = new JsonObject();
            json.put("nombre",cliente.getNombre());
            json.put("apellido",cliente.getApellido());
            json.put("edad",cliente.getEdad());
            json.put("fechaNacimiento",cliente.getFechaNacimiento());
            json.put("fechaMuerteAprox",fechaMuerte);

            clientes.add(json);
        }



       return clientes;

    }

    @PostMapping("/creacliente")
    public ResponseEntity<?> createClient(@RequestBody ClientEntity clientEntity){
        ClientEntity clientSave = clientRepository.save(clientEntity);
        return ResponseEntity.ok(clientSave);
    }

    @GetMapping("/kpideclientes")
    public String getPromedioDesviacionClients(){
        List<ClientEntity> clientes = clientRepository.findAll();
        if(clientes.size()>0){
            double sumaEdad = 0.0;
            double promedioEdad;
            double desviacion;
            double varianza= 0.0;
            double listSize = clientes.size();

            for(ClientEntity cliente : clientes){
                sumaEdad = sumaEdad + cliente.getEdad();
            }
            promedioEdad = sumaEdad/listSize;

            double sumatoria;
            for(ClientEntity cliente : clientes){
                sumatoria = Math.pow(cliente.getEdad()-promedioEdad, 2);
                varianza += sumatoria;
            }
            varianza = varianza/(listSize-1.0);

            desviacion = Math.sqrt(varianza);
            double redondeo = Math.rint(desviacion*100)/100;

            return "Promedio edad entre todos los clientes: " + promedioEdad +", Desviación estándar entre las edades de todos los clientes: "+ redondeo;
        }else{
            return "No existen clientes, porfavor crear al menos un cliente para poder realizar la consulta";

        }

    }

}

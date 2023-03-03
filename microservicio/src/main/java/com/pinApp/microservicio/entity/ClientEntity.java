package com.pinApp.microservicio.entity;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Getter @Setter
public class ClientEntity {

    @Id
    @Column(name ="id")
    @GeneratedValue
    private Long id;
    @Column(name ="nombre")
    private String nombre;
    @Column(name ="apellido")
    private String apellido;
    @Column(name ="edad")
    private Integer edad;
    @Column(name ="fecha_nacimiento")
    //@Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

}

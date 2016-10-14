/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.g5.bibliotecas.entities;

/**
 *
 * @author d.patino12
 */
import java.io.Serializable;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

  
    
    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entity> prestamos = new ArrayList<>();
    @OneToMany(mappedBy = "multa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultaEntity> multas = new ArrayList<>();
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entity> reservas = new ArrayList<>();
    
    private List<Integer> clave = new ArrayList<>();
    
    private String direccion;
     
   private String medioDePago;

    public List<Entity> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Entity> prestamos) {
        this.prestamos = prestamos;
    }

    public List<MultaEntity> getMultas() {
        return multas;
    }

    public void setMultas(List<MultaEntity> multas) {
        this.multas = multas;
    }

    public List<Entity> getReservas() {
        return reservas;
    }

    public void setReservas(List<Entity> reservas) {
        this.reservas = reservas;
    }

    public List<Integer> getClave() {
        return clave;
    }

    public void setClave(List<Integer> clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }
   
   

    
}
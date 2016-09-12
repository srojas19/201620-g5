/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.resources.dtos;

import java.util.Date;

/**
 *
 * @author sf.munera10
 */
public class PrestamoDTO {
    private Long id;
    private Long idUsuario;
    private Long idBiblioteca;
    private Long idRecurso;
    private double costo;
    private String medioPago;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean estaActivo;

    /**
     * Constructor por defecto
     */
    public PrestamoDTO() {
    }

    public PrestamoDTO(Long id, Long idUsuario, Long idBiblioteca, Long idRecurso, double costo, String medioPago, Date fechaInicial, Date fechaFinal, boolean estaActivo) {
        super();
        this.id = id;
        this.idUsuario = idUsuario;
        this.idBiblioteca = idBiblioteca;
        this.idRecurso = idRecurso;
        this.costo = costo;
        this.medioPago = medioPago;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estaActivo = estaActivo;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the idBiblioteca
     */
    public Long getIdBiblioteca() {
        return idBiblioteca;
    }

    /**
     * @param idBiblioteca the idBiblioteca to set
     */
    public void setIdBiblioteca(Long idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    /**
     * @return the idRecurso
     */
    public Long getIdRecurso() {
        return idRecurso;
    }

    /**
     * @param idRecurso the idRecurso to set
     */
    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the medioPago
     */
    public String getMedioPago() {
        return medioPago;
    }

    /**
     * @param medioPago the medioPago to set
     */
    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the estaActivo
     */
    public boolean isEstaActivo() {
        return estaActivo;
    }

    /**
     * @param estaActivo the estaActivo to set
     */
    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }
    
    /**
     * Convierte el objeto a una cadena
     */
    @Override
    public String toString() {
    	return "{ id : " + getId() + ", idUsuario : \"" + getIdUsuario()+ ", idBiblioteca : \"" + getIdBiblioteca() + ", idRecurso : \"" + getIdRecurso() + ", costo : \"" + getCosto() + ", medioPago : \"" + getMedioPago() + ", fechaInicial : \"" + getFechaInicial() + ", fechaFinal : \"" + getFechaFinal()  + ", estaActivo : \"" + isEstaActivo() + "\" }" ;  
    }
}
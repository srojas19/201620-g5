/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.g5.bibliotecas.ejbs;

import co.edu.uniandes.g5.bibliotecas.api.IPrestamoLogic;
import co.edu.uniandes.g5.bibliotecas.entities.BibliotecaEntity;
import co.edu.uniandes.g5.bibliotecas.entities.PrestamoEntity;
import co.edu.uniandes.g5.bibliotecas.entities.RecursoEntity;
import co.edu.uniandes.g5.bibliotecas.entities.UsuarioEntity;
import co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException;
import co.edu.uniandes.g5.bibliotecas.persistence.BibliotecaPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.LibroPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.PrestamoPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.SalaPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.UsuarioPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.VideoPersistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author ce.gonzalez13
 */
public class PrestamoLogic implements IPrestamoLogic {
    
    
    @Inject
    private PrestamoPersistence persistence;
    
    @Inject 
    private UsuarioPersistence usuarioPersistence;
    
    @Inject 
    private LibroPersistence libroPersistence;
    
    @Inject 
    private VideoPersistence videoPersistence;
    
    @Inject 
    private SalaPersistence salaPersistence;
    
    @Inject
    private BibliotecaPersistence bibliotecaPersistence;
    



   @Override
    public List<PrestamoEntity> getPrestamos() {
        return persistence.getPrestamos();
    }
    
    public List<PrestamoEntity> getPrestamosByBiblioteca(long idBiblioteca) {
        return persistence.getPrestamosByBiblioteca(idBiblioteca);
    }
    
    public List<PrestamoEntity> getPrestamosByUsuario(long idUsuario) {
        return persistence.getPrestamosByUsuario(idUsuario);
    }
    
    public List<PrestamoEntity> getPrestamosByRecurso(long idRecurso) {
        return persistence.getPrestamosByRecurso(idRecurso);
    }

    
    @Override
    public PrestamoEntity getPrestamo(Long id) {
        try {
            return persistence.getPrestamo(id);
        } catch (NoResultException e) {
            throw new IllegalArgumentException("El prestamo no existe");
        }
    }

    /**
     * Pre: El idUsuario corresponde a un usuario existente
     * El idBiblioteca corresponde a una biblioteca existente
     * El idRecurso corresponde a un recurso existente
     * tipoRecurso.equals("Libro")||tipoRecurso.equals("Video")||tipoRecurso.equals("Sala")
     * fechaInicial < fechaFinal
     * fechaInicial > Calendar.getInstance() (la fecha inicial es mayor a la fecha actual)
     * medioPago.equals("Tarjeta de credito")||medioPago.equals("Efectivo")||medioPago.equals("Tarjeta de debito")
     * 
     * 
     * @param idUsuario
     * @param idBiblioteca
     * @param idRecurso
     * @param costo
     * @param tipoRecurso
     * @param medioPago
     * @param fechaInicial
     * @param fechaFinal
     * @param estaActivo
     * @return PrestamoEntity
     * @throws co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException
     */
    @Override
    public PrestamoEntity createPrestamo(Long idUsuario, Long idBiblioteca, Long idRecurso,  Double costo,String tipoRecurso, String medioPago, Date fechaInicial, Date fechaFinal, boolean estaActivo) throws BibliotecaLogicException {
         
        UsuarioEntity usuario = usuarioPersistence.getUsuario(idUsuario);
        BibliotecaEntity biblioteca = bibliotecaPersistence.find(idBiblioteca);
        RecursoEntity recurso = null;
        switch (tipoRecurso) {
            case PrestamoEntity.LIBRO:
                recurso = libroPersistence.find(idRecurso);
                break;
            case PrestamoEntity.VIDEO:
                recurso = videoPersistence.find(idRecurso);
                break;
            case PrestamoEntity.SALA:
                recurso = salaPersistence.find(idRecurso);
                break;
            default:
                break;
        }
        
     
        if(costo <= 0 )
        {
            throw new BibliotecaLogicException("Costo inválido");
        }
        
       
                prestamo = new PrestamoEntity(usuario, biblioteca, recurso, costo,tipoRecurso, medioPago, fechaInicial, fechaFinal, estaActivo);
        }
        return persistence.create(prestamo);
        
    }

    

    @Override
    public PrestamoEntity updatePrestamo(Long idPrestamo, Long idUsuario, Long idBiblioteca, Long idRecurso,  Double costo,String tipoRecurso, String medioPago, Date fechaInicial, Date fechaFinal, boolean estaActivo) {
       
        PrestamoEntity prestamo = persistence.getPrestamo(idPrestamo);
        UsuarioEntity usuario = usuarioPersistence.getUsuario(idUsuario);
        BibliotecaEntity biblioteca = bibliotecaPersistence.find(idBiblioteca);
        RecursoEntity recurso = null;
        switch (tipoRecurso) {
            case PrestamoEntity.LIBRO:
                recurso = libroPersistence.find(idRecurso);
                break;
            case PrestamoEntity.VIDEO:
                recurso = videoPersistence.find(idRecurso);
                break;
            case PrestamoEntity.SALA:
                recurso = salaPersistence.find(idRecurso);
                break;
            default:
                break;
        }
        prestamo.setUsuario(usuario);
        prestamo.setBiblioteca(biblioteca);
        prestamo.setRecurso(recurso);
        prestamo.setCosto(costo);
        prestamo.setTipoRecurso(tipoRecurso);
        prestamo.setMedioPago(medioPago);
        prestamo.setFechaFinal(fechaFinal);
        prestamo.setFechaInicial(fechaInicial);
        prestamo.setEstaActivo(estaActivo);
        
        return persistence.update(prestamo);
        
    }

    /**
     *
     * @param idPrestamo
     * @return
     * @throws Exception
     */
    @Override
    public PrestamoEntity deletePrestamo(Long idPrestamo) throws Exception{
        PrestamoEntity prestamo = persistence.getPrestamo(idPrestamo);
        if(prestamo== null)
        {
            throw new Exception ("Se esta tratando de remover un prestamo inexistente");
        }
        persistence.delete(idPrestamo);
        return prestamo;
    }
    

    
}

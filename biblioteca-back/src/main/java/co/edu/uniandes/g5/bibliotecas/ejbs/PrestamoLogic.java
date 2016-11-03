/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.g5.bibliotecas.ejbs;

import co.edu.uniandes.g5.bibliotecas.api.IPrestamoLogic;
import co.edu.uniandes.g5.bibliotecas.entities.LibroEntity;
import co.edu.uniandes.g5.bibliotecas.entities.PrestamoEntity;
import co.edu.uniandes.g5.bibliotecas.entities.RecursoEntity;
import co.edu.uniandes.g5.bibliotecas.entities.VideoEntity;
import co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException;
import co.edu.uniandes.g5.bibliotecas.persistence.PrestamoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author ce.gonzalez13
 */
public class PrestamoLogic implements IPrestamoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PrestamoLogic.class.getName());
    
    
    @Inject
    private PrestamoPersistence persistence;

    @Override
    public List<PrestamoEntity> getPrestamos() {
        return persistence.getPrestamos();
    }
    
    @Override
    public List<PrestamoEntity> getPrestamosByBiblioteca(Long idBiblioteca) {
        return persistence.getPrestamosByBiblioteca(idBiblioteca);
    }
    @Override
    public List<PrestamoEntity> getPrestamosByUsuario(Long idBiblioteca, Long idUsuario) {
        return persistence.getPrestamosByUsuario(idBiblioteca,idUsuario);
    }
 
    /**
     *
     * @param idBiblioteca
     * @param idRecurso
     * @return
     */
    @Override
    public List<PrestamoEntity> getPrestamosByRecurso(Long idBiblioteca, Long idRecurso) {
        return persistence.getPrestamosByRecurso(idBiblioteca, idRecurso);
    }

    
    @Override
    public PrestamoEntity getPrestamo(Long id) {
        LOGGER.log(Level.INFO, "Consultando prestamo con id={0}", id);
        try {
            return persistence.getPrestamo(id);
        } catch (NoResultException e) {
            throw new IllegalArgumentException("El prestamo no existe");
        }
    }

    /**
     * Pre: prestamo.usuario corresponde a un usuario existente
     * El prestamo.biblioteca corresponde a una biblioteca existente
     * El prestamo.recurso corresponde a un recurso existente en la biblioteca actual.
     * prestamo.tipoRecurso.equals("Libro")||prestamo.tipoRecurso.equals("Video")||prestamo.tipoRecurso.equals("Sala")
     * prestamo.fechaInicial < prestamo.fechaFinal
     * prestamo.fechaInicial > Calendar.getInstance() (la fecha inicial es mayor a la fecha actual)
     * prestamo.medioPago.equals("Tarjeta de credito")||prestamo.medioPago.equals("Efectivo")||prestamo.medioPago.equals("Tarjeta de debito")
     * 
     * 
     * @param prestamo
     * @return PrestamoEntity
     * @throws co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException
     */
    @Override
    public PrestamoEntity createPrestamo(PrestamoEntity prestamo) throws BibliotecaLogicException {
         
     PrestamoEntity alreadyExist = getPrestamo(prestamo.getId());
        if (alreadyExist != null) 
        {
            throw new BibliotecaLogicException("Ya existe un prestamo con ese id");
        } 
        if(prestamo.getCosto() <= 0 )
        {
            throw new BibliotecaLogicException("Costo inválido. El costo no puede ser menor o igual a 0");
        }
        
        if(prestamo.getUsuario().getMultas().size() > 0)
        {
            throw new BibliotecaLogicException("El usuario tiene multas, no es posible hacer el préstamo hasta que se paguen las multas.");
        }

        if(prestamo.getRecurso().getTipoRecurso() == RecursoEntity.LIBRO)
        {
            LibroEntity libro = (LibroEntity) prestamo.getRecurso();
            if(libro.getEjemplaresDisponibles() <= 0)
            {
            throw new BibliotecaLogicException("No hay libros disponibles para prestar.");
            }
        }
        else if(prestamo.getRecurso().getTipoRecurso() == RecursoEntity.VIDEO)
        {
            VideoEntity video = (VideoEntity) prestamo.getRecurso();
            if(video.getEjemplaresDisponibles() == 0)
            throw new BibliotecaLogicException("No hay videos disponibles para prestar.");
        }
        
        prestamo = persistence.create(prestamo);

        
        return prestamo;
        
        
        
    }

    

    @Override
    public PrestamoEntity updatePrestamo(PrestamoEntity prestamo) throws BibliotecaLogicException {
       
         if(prestamo.getCosto() <= 0 )
        {
            throw new BibliotecaLogicException("Costo inválido");
        }
       if(prestamo.getRecurso().getTipoRecurso() == RecursoEntity.LIBRO)
        {
            LibroEntity libro = (LibroEntity) prestamo.getRecurso();
            if(libro.getEjemplaresDisponibles() == 0)
            throw new BibliotecaLogicException("No hay libros disponibles para prestar.");
        }
        else if(prestamo.getRecurso().getTipoRecurso() == RecursoEntity.VIDEO)
        {
            VideoEntity video = (VideoEntity) prestamo.getRecurso();
            if(video.getEjemplaresDisponibles() == 0)
            throw new BibliotecaLogicException("No hay videos disponibles para prestar.");
        }
        
        return persistence.update(prestamo);
        
    }

    /**
     *
     * @param idPrestamo
     * @return
     * @throws co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException
     */
    @Override
    public PrestamoEntity deletePrestamo(Long idPrestamo) throws BibliotecaLogicException{
        PrestamoEntity prestamo = persistence.getPrestamo(idPrestamo);
        if(prestamo== null)
        {
            throw new BibliotecaLogicException ("Se esta tratando de remover un prestamo inexistente");
        }
        persistence.delete(idPrestamo);
        return prestamo;
    }
    

    
}

/*
 * CityResource.java
 * Clase que representa el recurso "/cities"
 * Implementa varios métodos para manipular las ciudades
 */
package co.edu.uniandes.g5.bibliotecas.resources;

import co.edu.uniandes.g5.bibliotecas.api.IBibliotecaLogic;
import co.edu.uniandes.g5.bibliotecas.dtos.BiblioDTO;
import co.edu.uniandes.g5.bibliotecas.dtos.BiblioDetailDTO;
import co.edu.uniandes.g5.bibliotecas.entities.BibliotecaEntity;
import co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException;
import java.util.ArrayList;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Clase que implementa el recurso REST correspondiente a "cities".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "cities". Al ejecutar la aplicación, el recurse
 * será accesibe a través de la ruta "/api/cities"
 *
 * @author sf.munera10
 */
@Path("")
@Produces("application/json")
public class BiblioResource {

    @Inject
    private IBibliotecaLogic bibliotecaLogic;

    /**
     * Convierte una lista de BibliotecaEntity a una lista de
     * BibliotecaDetailDTO.
     *
     * @param entityList Lista de BibliotecaEntity a convertir.
     * @return Lista de BibliotecaDetailDTO convertida.
     *
     */
    private List<BiblioDetailDTO> listEntity2DTO(List<BibliotecaEntity> entityList) {
        List<BiblioDetailDTO> list = new ArrayList<>();
        for (BibliotecaEntity entity : entityList) {
            list.add(new BiblioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Obtiene el listado de bibliotecas.
     *
     * @return lista de bibliotecas
     * @throws BiblioLogicException excepción retornada por la lógica
     */
    @GET
    @Path("bibliotecas")
    public List<BiblioDetailDTO> getBibliotecas() throws BibliotecaLogicException {
        return listEntity2DTO(bibliotecaLogic.getBibliotecas());
    }

    /**
     * Agrega una biblioteca
     *
     * @param biblioteca biblioteca a agregar
     * @return datos de la biblioteca a agregar
     * @throws BiblioLogicException cuando ya existe una biblioteca con el id
     * suministrado
     */
    @POST
    @Path("bibliotecas")
    public BiblioDetailDTO createBiblioteca(BiblioDTO biblioteca) throws BibliotecaLogicException {
        return new BiblioDetailDTO(bibliotecaLogic.createBiblioteca(biblioteca.toEntity()));
    }

    @GET
    @Path("bibliotecas/{id: \\d+}")
    public BiblioDetailDTO getBiblioteca(@PathParam("id") Long id) throws BibliotecaLogicException {
        return new BiblioDetailDTO(bibliotecaLogic.getBiblioteca(id));
    }

    @PUT
    @Path("bibliotecas/{id: \\d+}")
    public BiblioDetailDTO updateBiblioteca(@PathParam("id") Long id, BiblioDTO b) throws BibliotecaLogicException {
        BibliotecaEntity entity = b.toEntity();
        entity.setId(id);
        return new BiblioDetailDTO(bibliotecaLogic.updateBiblioteca(entity));
    }

    @DELETE
    @Path("bibliotecas/{id: \\d+}")
    public void deleteBiblioteca(@PathParam("id") Long id) throws BibliotecaLogicException {
        bibliotecaLogic.deleteBiblioteca(id);
    }
}

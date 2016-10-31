/*
 * VideoResource.java
 * Clase que representa el recurso "/videos"
 * Implementa varios métodos para manipular los videos
 */
package co.edu.uniandes.g5.bibliotecas.resources;
import co.edu.uniandes.g5.bibliotecas.api.IVideoLogic;
import co.edu.uniandes.g5.bibliotecas.dtos.PrestamoDTO;
import co.edu.uniandes.g5.bibliotecas.dtos.VideoDTO;
import co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;
import javax.inject.Inject;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Clase que implementa el recurso REST correspondiente a "videos".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "videos". Al ejecutar la aplicación, el recurse
 * será accesibe a través de la ruta "/api/videos"
 *
 * @author ce.gonzalez13
 */
@Path("")
@Produces("application/json")
public class VideoResource
{

    @Inject
    IVideoLogic videoLogic;

    /**
     * Obtiene el listado de videos.
     *
     * @return lista de videos
     * @throws BibliotecaLogicException excepción retornada por la lógica
     */
    @GET
    @Path("videos")
    public List<VideoDTO> getVideos() throws BibliotecaLogicException {
        return videoLogic.getVideos();
    }
    
    /**
     * Obtiene el video especificado
     *
     * @param id video a buscar
     * @return el video buscado
     * @throws BibliotecaLogicException excepción retornada por la lógica
     */
    @GET
    @Path("videos/{id: \\d+}")
    public VideoDTO getVideo(@PathParam("id") Long id) throws BibliotecaLogicException {
        return videoLogic.getVideo(id);
    }
    
    @GET
    @Path("bibliotecas/{idBiblioteca: \\d+}/videos")
    public ArrayList<VideoDTO> getVideosBiblioteca(@PathParam("idBiblioteca") Long idBiblioteca) throws BibliotecaLogicException, ParseException {
        return videoLogic.getVideos(idBiblioteca);
    }


   
    /**
     * Agrega un video
     *
     * @param video video a agregar
     * @return datos del video a agregar
     * @throws BibliotecaLogicException cuando ya existe un video con el id
     * suministrado
     */
    @POST
    @Path("videos")
    public VideoDTO createVideo(VideoDTO video) throws BibliotecaLogicException {
        return videoLogic.createVideo(video);
    }
    
    /**
     * Actualiza un video
     *
     * @param id video a actualizar
     * @param newVideo video actualizado
     * @throws BibliotecaLogicException cuando el id no se encuentra
     * @return datos del video a actualizar
     */
    @PUT
    @Path("videos/{id: \\d+}")
    public VideoDTO updateVideo(@PathParam("id") Long id, VideoDTO newVideo) throws BibliotecaLogicException 
    {
        return videoLogic.updateVideo(id, newVideo);
    }
    
     /**
     * Elimina un video
     *
     * @param id id del video a eliminar
     * @throws BibliotecaLogicException cuando no existe el video que se quiere eliminar
     */
    @DELETE
    @Path("videos/{id: \\d+}")
    public void deleteVideo(@PathParam("id") Long id) throws BibliotecaLogicException 
    {
        videoLogic.deleteVideo(id);
    }
    
    

  
}
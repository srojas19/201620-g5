package co.edu.uniandes.rest.resources.mocks;

/**
 * Mock del recurso prestamos (Mock del servicio REST)
 *
 * @author sf.munera10
 */
import co.edu.uniandes.rest.resources.dtos.BiblioDTO;
import co.edu.uniandes.rest.resources.dtos.LibroDTO;
import co.edu.uniandes.rest.resources.dtos.MultaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.edu.uniandes.rest.resources.dtos.PrestamoDTO;
import co.edu.uniandes.rest.resources.dtos.SalaDTO;
import co.edu.uniandes.rest.resources.dtos.UsuarioDTO;
import co.edu.uniandes.rest.resources.dtos.VideoDTO;
import co.edu.uniandes.rest.resources.exceptions.BibliotecaLogicException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * PrestamoLogicMock
 * Mock del recurso prestamos (Mock del servicio REST)
 */
public class PrestamoLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(PrestamoLogicMock.class.getName());

    // listado de prestamos
    private static ArrayList<PrestamoDTO> prestamos;

    
    private UsuarioLogicMock usuarioMock;
    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public PrestamoLogicMock() {

        if (prestamos == null) {
            try {
                prestamos = new ArrayList<>();
                String fecha1 = "2016-09-25";
                String fecha2 = "2016-09-26";
                String fecha3 = "2016-09-27";
                String fecha4 = "2016-09-28";
                String fecha5 = "2016-09-29";
                String fecha6 = "2016-09-30";
                String fecha7 = "2016-10-01";
                String fecha8 = "2016-10-02";
                String fecha9 = "2016-10-03";
                String fecha10 = "2016-10-04";
                String fecha11 = "2016-10-05";
                String fecha12 = "2016-10-06";
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                usuarioMock = new UsuarioLogicMock();

                try {
                    UsuarioDTO u1 = usuarioMock.getUsuarios().get(0);
                    UsuarioDTO u2 = usuarioMock.getUsuarios().get(1);
                    UsuarioDTO u3 = usuarioMock.getUsuarios().get(2);
                
                
                prestamos.add(new PrestamoDTO(1L, u1,1L,PrestamoDTO.LIBRO, new LibroDTO(1L, 553213113L, "Moby Dick", 10L, true), 3000D, "Efectivo", formater.parse(fecha1), formater.parse(fecha2), true));
                prestamos.add(new PrestamoDTO(2L, u2, 2L,PrestamoDTO.VIDEO, new VideoDTO(1L, "The Revenant", "Alejandro G. Iñarritu",152L, 2015L, "Accion", 2L , "Oso contra hombre",false), 3000D, "Tarjeta Credito", formater.parse(fecha3), formater.parse(fecha4), true));
                prestamos.add(new PrestamoDTO(3L, u3, 3L,PrestamoDTO.SALA,  new SalaDTO(1L, 1L, "Sala 1", 6), 3000D, "Efectivo",formater.parse(fecha5),formater.parse(fecha6), true));
                prestamos.add(new PrestamoDTO(4L, u1, 1L,PrestamoDTO.VIDEO,  new VideoDTO(2L, "Mermaids: The Body Found", "Sid Bennet", 27L, 2012L,"Documental", 20L, "Sirenas", true ), 3000D, "Efectivo", formater.parse(fecha7), formater.parse(fecha8), true));
                prestamos.add(new PrestamoDTO(5L, u2, 2L,PrestamoDTO.SALA,  new SalaDTO(2L, 2L, "Sala 2", 6), 3000D, "Tarjeta Credito", formater.parse(fecha9), formater.parse(fecha10), true));
                prestamos.add(new PrestamoDTO(6L, u3, 3L,PrestamoDTO.LIBRO, new LibroDTO(2L, 743273567L, "The Great Gatsby", 10L, false), 3000D, "Efectivo", formater.parse(fecha11), formater.parse(fecha12), true));
                
            } catch (ParseException ex) {
                Logger.getLogger(PrestamoLogicMock.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (Exception ex) {
                    Logger.getLogger(PrestamoLogicMock.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa la lista de prestamos");
        logger.info("prestamos: " + prestamos);
    }

    /**
     * Obtiene el listado de personas.
     *
     * @return lista de prestamos
     * @throws BiblioLogicException cuando no existe la lista en memoria
     */
    public ArrayList<PrestamoDTO> getPrestamos() throws BibliotecaLogicException {
        if (prestamos == null) {
            logger.severe("Error interno: lista de prestamos no existe.");
            throw new BibliotecaLogicException("Error interno: lista de prestamos no existe.");
        }

        logger.info("retornando todas las prestamos");
        return prestamos;
    }
    
    public PrestamoDTO getPrestamo1(Long id) throws BibliotecaLogicException{
        PrestamoDTO p = null;
        for (int i = 0; i < prestamos.size() && p == null; i++) {
            PrestamoDTO prestamo = prestamos.get(i);
            if (prestamo.getId() == id) {
                p = prestamo;
            }
        }
        if (p == null) {
            logger.severe("No existe un prestamo con ese id");
            throw new BibliotecaLogicException("No existe un prestamo con ese id");
        }
        return p;
    }
    
    public PrestamoDTO updatePrestamo1(Long id, PrestamoDTO b) throws BibliotecaLogicException {
        PrestamoDTO c = getPrestamo1(id);
        if (c != null) {
            c.setEstaActivo(b.isEstaActivo());
            
            return c;
        } else {
            logger.severe("No existe un prestamo con ese id");
            throw new BibliotecaLogicException("No existe un prestamo con ese id");
        }
    }
    
    public ArrayList<PrestamoDTO> getPrestamo(Long idPrestamo) throws BibliotecaLogicException {
        ArrayList<PrestamoDTO> prestamosP = new ArrayList<>();
        if (prestamos == null) {
            logger.severe("Error interno: lista de prestamos no existe.");
            throw new BibliotecaLogicException("Error interno: lista de prestamos no existe.");
        }
        for (PrestamoDTO m : prestamos) {
            if (idPrestamo == m.getId()) {
                prestamosP.add(m);
            }
        }
        logger.info("retornando todos los prestamos");
        return prestamosP;
    }

    public ArrayList<PrestamoDTO> getPrestamosUsuario(Long idUsuario) throws BibliotecaLogicException {
        ArrayList<PrestamoDTO> prestamosUsuario = new ArrayList<>();
        if (prestamos == null) {
            logger.severe("Error interno: lista de prestamos no existe.");
            throw new BibliotecaLogicException("Error interno: lista de prestamos no existe.");
        }
        for (PrestamoDTO m : prestamos) {
            if (Objects.equals(idUsuario, m.getUsuario().getId())) {
                prestamosUsuario.add(m);
            }
        }
        logger.info("retornando todos los prestamos del usuario con id especificado");
        return prestamosUsuario;
    }

    
    
    /**
     * Agrega una prestamo a un usuario y a la lista.
     *
     * @param newPrestamo prestamo a adicionar
     * @throws BiblioLogicException cuando ya existe una biblioteca con el id
     * suministrado
     * @return biblioteca agregada
     */
    public PrestamoDTO createPrestamo(PrestamoDTO newPrestamo, Long idUsuario) throws BibliotecaLogicException {
        logger.info("recibiendo solicitud de agregar prestamo " + newPrestamo);
        // agrega el prestamo
        // el nuevo prestamo tiene id ?
        if (newPrestamo.getId() != null) {
            // busca la sala con el id suministrado segun el id de Biblioteca
            for (PrestamoDTO prestamo : prestamos) {
                // si existe una sala con ese id
                if (prestamo.getId() == newPrestamo.getId()) {
                    logger.severe("Ya existe un prestamo con ese id");
                    throw new BibliotecaLogicException("Ya existe un prestamo con ese id");
                }
            }

            // el nuevo prestamo no tiene id ? 
        } else {

   
                // genera un id para la sala
                logger.info("Generando id para el nuevo prestamo");
                logger.info("Generando idUsuario para el nuevo prestamo");
                long newId = 1;
                for (PrestamoDTO prestamo : prestamos) {
                    if (newId <= prestamo.getId()) {
                        newId = prestamo.getId() + 1;
                    }
                }
                newPrestamo.setId(newId);
                newPrestamo.setUsuario(getUsuario(idUsuario));
           
        }
        logger.info("agregando prestamo: " + newPrestamo);
        prestamos.add(newPrestamo);
        return newPrestamo;
    }

    public UsuarioDTO getUsuario( Long idUsuario)
    {
        try {
            return usuarioMock.getUsuario(idUsuario);
        } catch (Exception ex) {
            Logger.getLogger(PrestamoLogicMock.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    public PrestamoDTO getPrestamoDeUsuario(long id, long idUsuario) throws BibliotecaLogicException {
        PrestamoDTO m = null;
        List<PrestamoDTO> prestamosUsuario = getPrestamosUsuario(idUsuario);
        for (int i = 0; i < prestamosUsuario.size() && m == null; i++) {
            PrestamoDTO prestamo = prestamosUsuario.get(i);
            if (prestamo.getId() == id) {
                m = prestamo;
            }
        }
        if (m == null) {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
        return m;
    }
    
    public PrestamoDTO getPrestamoDeUsuario2(long id, long idUsuario) throws BibliotecaLogicException {
        PrestamoDTO m = null;
        for (int i = 0; i < prestamos.size() && m == null; i++) {
            PrestamoDTO prestamo = prestamos.get(i);
            if (prestamo.getId() == id && prestamo.getUsuario().getId() == idUsuario) {
                m = prestamo;
            }
        }
        if (m == null) {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
        return m;
    }

    public PrestamoDTO updatePrestamoDeUsuario(long id, PrestamoDTO m, Long idUsuario) throws BibliotecaLogicException {
        PrestamoDTO prestamo = getPrestamoDeUsuario(id, idUsuario);
        if (prestamo != null) {
            prestamo.setCosto(m.getCosto());
            prestamo.setFechaInicial(m.getFechaInicial());
            prestamo.setFechaFinal(m.getFechaFinal());
            prestamo.setEstaActivo(m.isEstaActivo());
            prestamo.setMedioPago(m.getMedioPago());
            prestamo.setId(id);
            prestamo.setIdBiblioteca(m.getIdBiblioteca());
            prestamo.setRecurso(m.getRecurso());
            
            prestamo.setUsuario(getUsuario(idUsuario));
            return prestamo;
        } else {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
    }
    
    public PrestamoDTO regresarLibro1(long id, PrestamoDTO m, Long idUsuario) throws BibliotecaLogicException {
        PrestamoDTO prestamo = getPrestamoDeUsuario2(id, idUsuario);
        if (prestamo != null) {
            prestamo.setCosto(m.getCosto());
            prestamo.setFechaInicial(m.getFechaInicial());
            prestamo.setFechaFinal(m.getFechaFinal());
            prestamo.setEstaActivo(false);
            prestamo.setMedioPago(m.getMedioPago());
            prestamo.setId(id);
            prestamo.setIdBiblioteca(m.getIdBiblioteca());
            prestamo.setRecurso(m.getRecurso());
            
            prestamo.setUsuario(getUsuario(idUsuario));
            return prestamo;
        } else {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
    }

    public PrestamoDTO regresarLibro(Long idRecurso, long idUsuario, String fechaEntrega) throws BibliotecaLogicException, ParseException {
        MultaLogicMock multaLogic = new MultaLogicMock();
        Date fecha = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        List<MultaDTO> multas = multaLogic.getMultas();
        PrestamoDTO prestamo = null;
        for (int i = 0; i < prestamos.size() && prestamo == null; i++) {
            PrestamoDTO p = prestamos.get(i);
            logger.info("recibiendo informacion para retornar libro...");
            if (p.getRecurso().getId() == idRecurso) {
                prestamo = p;
                MultaDTO multa = new MultaDTO(multas.size()+1L, idUsuario, p.getIdBiblioteca(), idRecurso, 1500, fecha);
                
                if(p.getFechaFinal().before(formatter.parse(fechaEntrega))){
                    multaLogic.createMultaUsuario(multa, idUsuario);
                }
            }
        }
        if (prestamo == null) {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
        return prestamo;
    }

    public void deletePrestamoDeUsuario(long id, long idUsuario) throws BibliotecaLogicException {
        logger.info("recibiendo solicitud de eliminar prestamo " + id);
        PrestamoDTO prestamo = getPrestamoDeUsuario(id, idUsuario);
        if (prestamo != null) {
            logger.info("Eliminando prestamo con el id especfificado: id = " + prestamo.getId());
            prestamos.remove(prestamo);
        } else {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
    }

    /**
     * R12 Ver prestamos activos de un libro
     * @param id id del libro a consultar
     * @return lista de los prestamos activos del libro
     */
    public List<PrestamoDTO> getPrestamosLibro(Long id) {
        ArrayList<PrestamoDTO> presLib;
        presLib = new ArrayList<>();
        for (PrestamoDTO p : prestamos) {
            if (id.equals(p.getRecurso().getId()) && p.isEstaActivo() && p.getTipoRecurso().equals(PrestamoDTO.LIBRO)) {
                presLib.add(p);
            }
        }
        return presLib; 
    }
    
   

    public ArrayList<PrestamoDTO> getPrestamosBiblioteca(Long idBiblioteca) throws BibliotecaLogicException {
        ArrayList<PrestamoDTO> prestamosBiblioteca = new ArrayList<>();
        if (prestamos == null) {
            logger.severe("Error interno: lista de prestamos no existe.");
            throw new BibliotecaLogicException("Error interno: lista de prestamos no existe.");
        }
        for (PrestamoDTO m : prestamos) {
            if (idBiblioteca == m.getIdBiblioteca()) {
                prestamosBiblioteca.add(m);
            }
        }
        logger.info("retornando todos los prestamos del usuario con id especificado");
        return prestamosBiblioteca;
    }


    public PrestamoDTO getPrestamoDeBiblioteca(long id, long idBiblioteca) throws BibliotecaLogicException {
        PrestamoDTO m = null;
        List<PrestamoDTO> prestamosBiblioteca = getPrestamosBiblioteca(idBiblioteca);
        for (int i = 0; i < prestamosBiblioteca.size() && m == null; i++) {
            PrestamoDTO prestamo = prestamosBiblioteca.get(i);
            if (prestamo.getId() == id) {
                m = prestamo;
            }
        }
        if (m == null) {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
        return m;
    }

     /**
     * Agrega una prestamo a un usuario y a la lista.
     *
     * @param newPrestamo prestamo a adicionar
     * @throws BiblioLogicException cuando ya existe una biblioteca con el id
     * suministrado
     * @return biblioteca agregada
     */
    public PrestamoDTO createPrestamoBiblioteca(PrestamoDTO newPrestamo, Long idBiblioteca) throws BibliotecaLogicException {
        logger.info("recibiendo solicitud de agregar prestamo " + newPrestamo);
        // agrega el prestamo
        // el nuevo prestamo tiene id ?
        if (newPrestamo.getId() != null) {
            // busca la sala con el id suministrado segun el id de Biblioteca
            for (PrestamoDTO prestamo : prestamos) {
                // si existe una sala con ese id
                if (prestamo.getId() == newPrestamo.getId()) {
                    logger.severe("Ya existe un prestamo con ese id");
                    throw new BibliotecaLogicException("Ya existe un prestamo con ese id");
                }
            }

            // el nuevo prestamo no tiene id ? 
        } else {

            // genera un id para la sala
            logger.info("Generando id para el nuevo prestamo");
            logger.info("Generando idUsuario para el nuevo prestamo");
            long newId = 1;
            for (PrestamoDTO prestamo : prestamos) {
                if (newId <= prestamo.getId()) {
                    newId = prestamo.getId() + 1;
                }
            }
            newPrestamo.setId(newId);
            newPrestamo.setIdBiblioteca(idBiblioteca);
            newPrestamo.getRecurso().setEstaPrestado(true);
        }
        logger.info("agregando prestamo: " + newPrestamo);
        prestamos.add(newPrestamo);
        return newPrestamo;
    }
    
     public PrestamoDTO deletePrestamoDeBiblioteca(long id, long idBiblioteca) throws BibliotecaLogicException {
        logger.info("recibiendo solicitud de eliminar prestamo " + id);
        PrestamoDTO prestamo = getPrestamoDeBiblioteca(id, idBiblioteca);
        if (prestamo != null) {
            logger.info("Eliminando prestamo con el id especfificado: id = " + prestamo.getId());
            prestamos.remove(prestamo);
            return prestamo;
        } else {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
    }
     
     public PrestamoDTO updatePrestamoDeBiblioteca(long id, PrestamoDTO m, Long idBiblioteca) throws BibliotecaLogicException {
        PrestamoDTO prestamo = getPrestamoDeBiblioteca(id, idBiblioteca);
        if (prestamo != null) {
            prestamo.setCosto(m.getCosto());
            prestamo.setFechaInicial(m.getFechaInicial());
            prestamo.setFechaFinal(m.getFechaFinal());
            prestamo.setEstaActivo(m.isEstaActivo());
            prestamo.setMedioPago(m.getMedioPago());
            prestamo.setId(id);
            prestamo.setUsuario(m.getUsuario());
            prestamo.setRecurso(m.getRecurso());
            
            prestamo.setIdBiblioteca(idBiblioteca);
            return prestamo;
        } else {
            logger.severe("No existe una prestamo con ese id");
            throw new BibliotecaLogicException("No existe una prestamo con ese id");
        }
    }

}

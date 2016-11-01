/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.g5.bibliotecas.testLogic;

import co.edu.uniandes.g5.bibliotecas.api.IVideoLogic;
import co.edu.uniandes.g5.bibliotecas.ejbs.VideoLogic;
import co.edu.uniandes.g5.bibliotecas.entities.BibliotecaEntity;
import co.edu.uniandes.g5.bibliotecas.entities.VideoEntity;
import co.edu.uniandes.g5.bibliotecas.exceptions.BibliotecaLogicException;
import co.edu.uniandes.g5.bibliotecas.persistence.BibliotecaPersistence;
import co.edu.uniandes.g5.bibliotecas.persistence.VideoPersistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 */
@RunWith(Arquillian.class)
public class VideoLogicTest {

    /**
     *
     */
    BibliotecaEntity fatherEntity;

    /**
     *
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     *
     */
    @Inject
    private IVideoLogic videoLogic;

    /**
     *
     */
    @PersistenceContext
    private EntityManager em;

    /**
     *
     */
    @Inject
    private UserTransaction utx;

    /**
     *
     */
    private List<VideoEntity> videoData = new ArrayList<VideoEntity>();

    /**
     *
     */
    private List<BibliotecaEntity> bibliotecaData = new ArrayList<>();

    /**
     *
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VideoEntity.class.getPackage())
                .addPackage(VideoLogic.class.getPackage())
                .addPackage(IVideoLogic.class.getPackage())
                .addPackage(VideoPersistence.class.getPackage())
                .addPackage(BibliotecaEntity.class.getPackage())
                .addPackage(BibliotecaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     *
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from VideoEntity").executeUpdate();
        em.createQuery("delete from BibliotecaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        fatherEntity = factory.manufacturePojo(BibliotecaEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            VideoEntity entity = factory.manufacturePojo(VideoEntity.class);
            entity.setBiblioteca(fatherEntity);
            entity.setEjemplaresDisponibles(entity.getNumEjemplares());
            em.persist(entity);
            videoData.add(entity);
        }
    }

    /**
     * Prueba para crear un Video
     *
     *
     */
    @Test
    public void testCreateVideo1() throws BibliotecaLogicException {
        VideoEntity newEntity = factory.manufacturePojo(VideoEntity.class);
        VideoEntity result = videoLogic.createVideo(newEntity);
        Assert.assertNotNull(result);
        VideoEntity entity = em.find(VideoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para crear un video con una biblioteca que no existe
     */
    @Test(expected = BibliotecaLogicException.class)
    public void testCreateVideo2() throws Exception {
        VideoEntity entity = factory.manufacturePojo(VideoEntity.class);
        BibliotecaEntity noExiste = factory.manufacturePojo(BibliotecaEntity.class);
        entity.setBiblioteca(noExiste);
        VideoEntity result = videoLogic.createVideo(entity);
    }

    /**
     * Prueba para crear un video con un nombre que ya existe
     */
    @Test(expected = BibliotecaLogicException.class)
    public void testCreateVideo3() throws Exception {
        VideoEntity entity = factory.manufacturePojo(VideoEntity.class);
        entity.setBiblioteca(fatherEntity);
        entity.setName(videoData.get(0).getName());
        VideoEntity result = videoLogic.createVideo(entity);
    }

    /**
     * Prueba para crear un video que tiene mas unidades disponibles que su
     * cantidad total
     */
    @Test(expected = BibliotecaLogicException.class)
    public void testCreateVideo4() throws Exception {
        VideoEntity entity = factory.manufacturePojo(VideoEntity.class);
        entity.setBiblioteca(fatherEntity);
        entity.setEjemplaresDisponibles(entity.getNumEjemplares() + 1);
        VideoEntity result = videoLogic.createVideo(entity);
    }

    /**
     * Prueba para consultar la lista de Videos
     *
     *
     */
    @Test
    public void testGetVideos() {
        List<VideoEntity> list = videoLogic.getVideos();
        Assert.assertEquals(videoData.size(), list.size());
        for (VideoEntity entity : list) {
            boolean found = false;
            for (VideoEntity storedEntity : videoData) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Video
     *
     *
     */
    @Test
    public void testGetVideo() {
        VideoEntity entity = videoData.get(0);
        VideoEntity resultEntity = videoLogic.getVideo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Prueba para eliminar un Video
     *
     *
     */
    @Test
    public void testDeleteVideo() {
        VideoEntity entity = videoData.get(1);
        videoLogic.deleteVideo(entity.getId());
        VideoEntity deleted = em.find(VideoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Video
     *
     *
     */
    @Test
    public void testUpdateVideo1() throws BibliotecaLogicException {
        VideoEntity entity = videoData.get(0);
        VideoEntity pojoEntity = factory.manufacturePojo(VideoEntity.class);

        pojoEntity.setId(entity.getId());

        videoLogic.updateVideo(pojoEntity);

        VideoEntity resp = em.find(VideoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    @Test(expected = BibliotecaLogicException.class)
    public void testUpdateVideo2() throws BibliotecaLogicException {
        VideoEntity entity = videoData.get(0);
        VideoEntity pojoEntity = factory.manufacturePojo(VideoEntity.class);
        BibliotecaEntity noExiste = factory.manufacturePojo(BibliotecaEntity.class);
        pojoEntity.setBiblioteca(noExiste);
        pojoEntity.setId(entity.getId());

        VideoEntity resp = videoLogic.updateVideo(pojoEntity);
    }

    @Test(expected = BibliotecaLogicException.class)
    public void testUpdateVideo3() throws BibliotecaLogicException {
        VideoEntity entity = videoData.get(0);
        VideoEntity pojoEntity = factory.manufacturePojo(VideoEntity.class);
        entity.setEjemplaresDisponibles(entity.getNumEjemplares() + 1);
        pojoEntity.setId(entity.getId());
        VideoEntity resp = videoLogic.updateVideo(pojoEntity);
    }

    /**
     * Test of getVideos method, of class VideoLogic.
     */
    @Test
    public void testGetVideosByBiblioteca() {
        List<VideoEntity> list = videoLogic.getVideosByBiblioteca(fatherEntity.getId());
        Assert.assertEquals(videoData.size(), list.size());
        for (VideoEntity entity : list) {
            boolean found = false;
            for (VideoEntity storedEntity : videoData) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of getVideoByName method, of class VideoLogic.
     */
    @Test
    public void testGetVideoByName() {
        VideoEntity entity = videoData.get(0);
        VideoEntity resultEntity = videoLogic.getVideoByName(entity.getName(), entity.getBiblioteca().getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.g5.bibliotecas.api;

import co.edu.uniandes.g5.bibliotecas.entities.BlogEntity;
import java.util.List;

/**
 *
 * @author js.prieto10
 */
public interface IBlogLogic {
    public List<BlogEntity> getBlogs();
    public BlogEntity getBlog(Long id);
    public BlogEntity createBlog(BlogEntity entity); 
    public BlogEntity updateBlog(BlogEntity entity);
    public void deleteBlog(Long id);
}
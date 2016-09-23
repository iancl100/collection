/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.collection.model.dao;

/**
 *
 * @author 31535811
 */
import com.br.collection.model.entities.Obra;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author First Place
 */
@Stateful
@LocalBean
public class ObraDAO implements GenericDAO<Obra>{
    
    @PersistenceContext(unitName="Collection-ejbPU", type= PersistenceContextType.TRANSACTION)
    EntityManager em;
    
    @Override
    public void create(Obra e) {
        em.persist(e);
    }

    @Override
    public List<Obra> read() {
        Query query = em.createNamedQuery("Obra.findAll");
        return (List<Obra>)query.getResultList();
    }

    @Override
    public Obra readById(long id) {
        return em.find(Obra.class, id);
    }
    public Obra readByName(String nome){
        Query query = em.createNamedQuery("Obra.findByNome").setParameter("nome", nome);
        Object obj= null;
        try{
            obj=query.getSingleResult();
            return (Obra)obj; 
        }catch(NoResultException ex){
            return null;
        }
    }

    @Override
    public void update(Obra e) {
        em.merge(e);
    }

    @Override
    public void delete(Obra e) {
        em.merge(e);
        em.remove(e);
    }
}

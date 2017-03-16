/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.controller;

import dao.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TransaksiTipe;

/**
 *
 * @author fachrul
 */
public class TransaksiTipeJpaController implements Serializable {

    public TransaksiTipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransaksiTipe transaksiTipe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(transaksiTipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransaksiTipe transaksiTipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            transaksiTipe = em.merge(transaksiTipe);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaksiTipe.getTransaksiTipeId();
                if (findTransaksiTipe(id) == null) {
                    throw new NonexistentEntityException("The transaksiTipe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransaksiTipe transaksiTipe;
            try {
                transaksiTipe = em.getReference(TransaksiTipe.class, id);
                transaksiTipe.getTransaksiTipeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksiTipe with id " + id + " no longer exists.", enfe);
            }
            em.remove(transaksiTipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransaksiTipe> findTransaksiTipeEntities() {
        return findTransaksiTipeEntities(true, -1, -1);
    }

    public List<TransaksiTipe> findTransaksiTipeEntities(int maxResults, int firstResult) {
        return findTransaksiTipeEntities(false, maxResults, firstResult);
    }

    private List<TransaksiTipe> findTransaksiTipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransaksiTipe.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TransaksiTipe findTransaksiTipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransaksiTipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiTipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransaksiTipe> rt = cq.from(TransaksiTipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

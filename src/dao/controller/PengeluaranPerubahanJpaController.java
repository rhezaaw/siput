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
import model.Pengeluaran;
import model.PengeluaranPerubahan;

/**
 *
 * @author fachrul
 */
public class PengeluaranPerubahanJpaController implements Serializable {

    public PengeluaranPerubahanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PengeluaranPerubahan pengeluaranPerubahan) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pengeluaran pengeluaranId = pengeluaranPerubahan.getPengeluaranId();
            if (pengeluaranId != null) {
                pengeluaranId = em.getReference(pengeluaranId.getClass(), pengeluaranId.getPengeluaranId());
                pengeluaranPerubahan.setPengeluaranId(pengeluaranId);
            }
            em.persist(pengeluaranPerubahan);
            if (pengeluaranId != null) {
                pengeluaranId.getPengeluaranPerubahanList().add(pengeluaranPerubahan);
                pengeluaranId = em.merge(pengeluaranId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PengeluaranPerubahan pengeluaranPerubahan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PengeluaranPerubahan persistentPengeluaranPerubahan = em.find(PengeluaranPerubahan.class, pengeluaranPerubahan.getPengeluaranPerubahanId());
            Pengeluaran pengeluaranIdOld = persistentPengeluaranPerubahan.getPengeluaranId();
            Pengeluaran pengeluaranIdNew = pengeluaranPerubahan.getPengeluaranId();
            if (pengeluaranIdNew != null) {
                pengeluaranIdNew = em.getReference(pengeluaranIdNew.getClass(), pengeluaranIdNew.getPengeluaranId());
                pengeluaranPerubahan.setPengeluaranId(pengeluaranIdNew);
            }
            pengeluaranPerubahan = em.merge(pengeluaranPerubahan);
            if (pengeluaranIdOld != null && !pengeluaranIdOld.equals(pengeluaranIdNew)) {
                pengeluaranIdOld.getPengeluaranPerubahanList().remove(pengeluaranPerubahan);
                pengeluaranIdOld = em.merge(pengeluaranIdOld);
            }
            if (pengeluaranIdNew != null && !pengeluaranIdNew.equals(pengeluaranIdOld)) {
                pengeluaranIdNew.getPengeluaranPerubahanList().add(pengeluaranPerubahan);
                pengeluaranIdNew = em.merge(pengeluaranIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pengeluaranPerubahan.getPengeluaranPerubahanId();
                if (findPengeluaranPerubahan(id) == null) {
                    throw new NonexistentEntityException("The pengeluaranPerubahan with id " + id + " no longer exists.");
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
            PengeluaranPerubahan pengeluaranPerubahan;
            try {
                pengeluaranPerubahan = em.getReference(PengeluaranPerubahan.class, id);
                pengeluaranPerubahan.getPengeluaranPerubahanId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pengeluaranPerubahan with id " + id + " no longer exists.", enfe);
            }
            Pengeluaran pengeluaranId = pengeluaranPerubahan.getPengeluaranId();
            if (pengeluaranId != null) {
                pengeluaranId.getPengeluaranPerubahanList().remove(pengeluaranPerubahan);
                pengeluaranId = em.merge(pengeluaranId);
            }
            em.remove(pengeluaranPerubahan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PengeluaranPerubahan> findPengeluaranPerubahanEntities() {
        return findPengeluaranPerubahanEntities(true, -1, -1);
    }

    public List<PengeluaranPerubahan> findPengeluaranPerubahanEntities(int maxResults, int firstResult) {
        return findPengeluaranPerubahanEntities(false, maxResults, firstResult);
    }

    private List<PengeluaranPerubahan> findPengeluaranPerubahanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PengeluaranPerubahan.class));
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

    public PengeluaranPerubahan findPengeluaranPerubahan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PengeluaranPerubahan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPengeluaranPerubahanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PengeluaranPerubahan> rt = cq.from(PengeluaranPerubahan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

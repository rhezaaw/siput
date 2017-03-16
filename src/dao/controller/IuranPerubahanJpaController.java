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
import model.Iuran;
import model.IuranPerubahan;

/**
 *
 * @author fachrul
 */
public class IuranPerubahanJpaController implements Serializable {

    public IuranPerubahanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IuranPerubahan iuranPerubahan) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iuran iuranId = iuranPerubahan.getIuranId();
            if (iuranId != null) {
                iuranId = em.getReference(iuranId.getClass(), iuranId.getIuranId());
                iuranPerubahan.setIuranId(iuranId);
            }
            em.persist(iuranPerubahan);
            if (iuranId != null) {
                iuranId.getIuranPerubahanList().add(iuranPerubahan);
                iuranId = em.merge(iuranId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IuranPerubahan iuranPerubahan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IuranPerubahan persistentIuranPerubahan = em.find(IuranPerubahan.class, iuranPerubahan.getIuranPerubahanId());
            Iuran iuranIdOld = persistentIuranPerubahan.getIuranId();
            Iuran iuranIdNew = iuranPerubahan.getIuranId();
            if (iuranIdNew != null) {
                iuranIdNew = em.getReference(iuranIdNew.getClass(), iuranIdNew.getIuranId());
                iuranPerubahan.setIuranId(iuranIdNew);
            }
            iuranPerubahan = em.merge(iuranPerubahan);
            if (iuranIdOld != null && !iuranIdOld.equals(iuranIdNew)) {
                iuranIdOld.getIuranPerubahanList().remove(iuranPerubahan);
                iuranIdOld = em.merge(iuranIdOld);
            }
            if (iuranIdNew != null && !iuranIdNew.equals(iuranIdOld)) {
                iuranIdNew.getIuranPerubahanList().add(iuranPerubahan);
                iuranIdNew = em.merge(iuranIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iuranPerubahan.getIuranPerubahanId();
                if (findIuranPerubahan(id) == null) {
                    throw new NonexistentEntityException("The iuranPerubahan with id " + id + " no longer exists.");
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
            IuranPerubahan iuranPerubahan;
            try {
                iuranPerubahan = em.getReference(IuranPerubahan.class, id);
                iuranPerubahan.getIuranPerubahanId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iuranPerubahan with id " + id + " no longer exists.", enfe);
            }
            Iuran iuranId = iuranPerubahan.getIuranId();
            if (iuranId != null) {
                iuranId.getIuranPerubahanList().remove(iuranPerubahan);
                iuranId = em.merge(iuranId);
            }
            em.remove(iuranPerubahan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IuranPerubahan> findIuranPerubahanEntities() {
        return findIuranPerubahanEntities(true, -1, -1);
    }

    public List<IuranPerubahan> findIuranPerubahanEntities(int maxResults, int firstResult) {
        return findIuranPerubahanEntities(false, maxResults, firstResult);
    }

    private List<IuranPerubahan> findIuranPerubahanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IuranPerubahan.class));
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

    public IuranPerubahan findIuranPerubahan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IuranPerubahan.class, id);
        } finally {
            em.close();
        }
    }

    public int getIuranPerubahanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IuranPerubahan> rt = cq.from(IuranPerubahan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

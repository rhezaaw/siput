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
import model.Deposit;
import model.DepositPerubahan;

/**
 *
 * @author fachrul
 */
public class DepositPerubahanJpaController implements Serializable {

    public DepositPerubahanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DepositPerubahan depositPerubahan) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deposit depositId = depositPerubahan.getDepositId();
            if (depositId != null) {
                depositId = em.getReference(depositId.getClass(), depositId.getDepositId());
                depositPerubahan.setDepositId(depositId);
            }
            em.persist(depositPerubahan);
            if (depositId != null) {
                depositId.getDepositPerubahanList().add(depositPerubahan);
                depositId = em.merge(depositId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DepositPerubahan depositPerubahan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepositPerubahan persistentDepositPerubahan = em.find(DepositPerubahan.class, depositPerubahan.getDepositPerubahanId());
            Deposit depositIdOld = persistentDepositPerubahan.getDepositId();
            Deposit depositIdNew = depositPerubahan.getDepositId();
            if (depositIdNew != null) {
                depositIdNew = em.getReference(depositIdNew.getClass(), depositIdNew.getDepositId());
                depositPerubahan.setDepositId(depositIdNew);
            }
            depositPerubahan = em.merge(depositPerubahan);
            if (depositIdOld != null && !depositIdOld.equals(depositIdNew)) {
                depositIdOld.getDepositPerubahanList().remove(depositPerubahan);
                depositIdOld = em.merge(depositIdOld);
            }
            if (depositIdNew != null && !depositIdNew.equals(depositIdOld)) {
                depositIdNew.getDepositPerubahanList().add(depositPerubahan);
                depositIdNew = em.merge(depositIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = depositPerubahan.getDepositPerubahanId();
                if (findDepositPerubahan(id) == null) {
                    throw new NonexistentEntityException("The depositPerubahan with id " + id + " no longer exists.");
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
            DepositPerubahan depositPerubahan;
            try {
                depositPerubahan = em.getReference(DepositPerubahan.class, id);
                depositPerubahan.getDepositPerubahanId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The depositPerubahan with id " + id + " no longer exists.", enfe);
            }
            Deposit depositId = depositPerubahan.getDepositId();
            if (depositId != null) {
                depositId.getDepositPerubahanList().remove(depositPerubahan);
                depositId = em.merge(depositId);
            }
            em.remove(depositPerubahan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DepositPerubahan> findDepositPerubahanEntities() {
        return findDepositPerubahanEntities(true, -1, -1);
    }

    public List<DepositPerubahan> findDepositPerubahanEntities(int maxResults, int firstResult) {
        return findDepositPerubahanEntities(false, maxResults, firstResult);
    }

    private List<DepositPerubahan> findDepositPerubahanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DepositPerubahan.class));
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

    public DepositPerubahan findDepositPerubahan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DepositPerubahan.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepositPerubahanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DepositPerubahan> rt = cq.from(DepositPerubahan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

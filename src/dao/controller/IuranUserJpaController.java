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
import model.IuranUser;
import model.User;
import model.Transaksi;

/**
 *
 * @author fachrul
 */
public class IuranUserJpaController implements Serializable {

    public IuranUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IuranUser iuranUser) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iuran iuranId = iuranUser.getIuranId();
            if (iuranId != null) {
                iuranId = em.getReference(iuranId.getClass(), iuranId.getIuranId());
                iuranUser.setIuranId(iuranId);
            }
            User userId = iuranUser.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                iuranUser.setUserId(userId);
            }
            Transaksi transaksiId = iuranUser.getTransaksiId();
            if (transaksiId != null) {
                transaksiId = em.getReference(transaksiId.getClass(), transaksiId.getTransaksiId());
                iuranUser.setTransaksiId(transaksiId);
            }
            em.persist(iuranUser);
            if (iuranId != null) {
                iuranId.getIuranUserList().add(iuranUser);
                iuranId = em.merge(iuranId);
            }
            if (userId != null) {
                userId.getIuranUserList().add(iuranUser);
                userId = em.merge(userId);
            }
            if (transaksiId != null) {
                transaksiId.getIuranUserList().add(iuranUser);
                transaksiId = em.merge(transaksiId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IuranUser iuranUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IuranUser persistentIuranUser = em.find(IuranUser.class, iuranUser.getIuranUserId());
            Iuran iuranIdOld = persistentIuranUser.getIuranId();
            Iuran iuranIdNew = iuranUser.getIuranId();
            User userIdOld = persistentIuranUser.getUserId();
            User userIdNew = iuranUser.getUserId();
            Transaksi transaksiIdOld = persistentIuranUser.getTransaksiId();
            Transaksi transaksiIdNew = iuranUser.getTransaksiId();
            if (iuranIdNew != null) {
                iuranIdNew = em.getReference(iuranIdNew.getClass(), iuranIdNew.getIuranId());
                iuranUser.setIuranId(iuranIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                iuranUser.setUserId(userIdNew);
            }
            if (transaksiIdNew != null) {
                transaksiIdNew = em.getReference(transaksiIdNew.getClass(), transaksiIdNew.getTransaksiId());
                iuranUser.setTransaksiId(transaksiIdNew);
            }
            iuranUser = em.merge(iuranUser);
            if (iuranIdOld != null && !iuranIdOld.equals(iuranIdNew)) {
                iuranIdOld.getIuranUserList().remove(iuranUser);
                iuranIdOld = em.merge(iuranIdOld);
            }
            if (iuranIdNew != null && !iuranIdNew.equals(iuranIdOld)) {
                iuranIdNew.getIuranUserList().add(iuranUser);
                iuranIdNew = em.merge(iuranIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getIuranUserList().remove(iuranUser);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getIuranUserList().add(iuranUser);
                userIdNew = em.merge(userIdNew);
            }
            if (transaksiIdOld != null && !transaksiIdOld.equals(transaksiIdNew)) {
                transaksiIdOld.getIuranUserList().remove(iuranUser);
                transaksiIdOld = em.merge(transaksiIdOld);
            }
            if (transaksiIdNew != null && !transaksiIdNew.equals(transaksiIdOld)) {
                transaksiIdNew.getIuranUserList().add(iuranUser);
                transaksiIdNew = em.merge(transaksiIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iuranUser.getIuranUserId();
                if (findIuranUser(id) == null) {
                    throw new NonexistentEntityException("The iuranUser with id " + id + " no longer exists.");
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
            IuranUser iuranUser;
            try {
                iuranUser = em.getReference(IuranUser.class, id);
                iuranUser.getIuranUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iuranUser with id " + id + " no longer exists.", enfe);
            }
            Iuran iuranId = iuranUser.getIuranId();
            if (iuranId != null) {
                iuranId.getIuranUserList().remove(iuranUser);
                iuranId = em.merge(iuranId);
            }
            User userId = iuranUser.getUserId();
            if (userId != null) {
                userId.getIuranUserList().remove(iuranUser);
                userId = em.merge(userId);
            }
            Transaksi transaksiId = iuranUser.getTransaksiId();
            if (transaksiId != null) {
                transaksiId.getIuranUserList().remove(iuranUser);
                transaksiId = em.merge(transaksiId);
            }
            em.remove(iuranUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IuranUser> findIuranUserEntities() {
        return findIuranUserEntities(true, -1, -1);
    }

    public List<IuranUser> findIuranUserEntities(int maxResults, int firstResult) {
        return findIuranUserEntities(false, maxResults, firstResult);
    }

    private List<IuranUser> findIuranUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IuranUser.class));
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

    public IuranUser findIuranUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IuranUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getIuranUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IuranUser> rt = cq.from(IuranUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

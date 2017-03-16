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
import model.User;
import model.Iuran;
import model.PengeluaranUser;
import model.Transaksi;

/**
 *
 * @author fachrul
 */
public class PengeluaranUserJpaController implements Serializable {

    public PengeluaranUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PengeluaranUser pengeluaranUser) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = pengeluaranUser.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                pengeluaranUser.setUserId(userId);
            }
            Iuran iuranId = pengeluaranUser.getIuranId();
            if (iuranId != null) {
                iuranId = em.getReference(iuranId.getClass(), iuranId.getIuranId());
                pengeluaranUser.setIuranId(iuranId);
            }
            Transaksi transaksiId = pengeluaranUser.getTransaksiId();
            if (transaksiId != null) {
                transaksiId = em.getReference(transaksiId.getClass(), transaksiId.getTransaksiId());
                pengeluaranUser.setTransaksiId(transaksiId);
            }
            em.persist(pengeluaranUser);
            if (userId != null) {
                userId.getPengeluaranUserList().add(pengeluaranUser);
                userId = em.merge(userId);
            }
            if (iuranId != null) {
                iuranId.getPengeluaranUserList().add(pengeluaranUser);
                iuranId = em.merge(iuranId);
            }
            if (transaksiId != null) {
                transaksiId.getPengeluaranUserList().add(pengeluaranUser);
                transaksiId = em.merge(transaksiId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PengeluaranUser pengeluaranUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PengeluaranUser persistentPengeluaranUser = em.find(PengeluaranUser.class, pengeluaranUser.getPengeluaranUserId());
            User userIdOld = persistentPengeluaranUser.getUserId();
            User userIdNew = pengeluaranUser.getUserId();
            Iuran iuranIdOld = persistentPengeluaranUser.getIuranId();
            Iuran iuranIdNew = pengeluaranUser.getIuranId();
            Transaksi transaksiIdOld = persistentPengeluaranUser.getTransaksiId();
            Transaksi transaksiIdNew = pengeluaranUser.getTransaksiId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                pengeluaranUser.setUserId(userIdNew);
            }
            if (iuranIdNew != null) {
                iuranIdNew = em.getReference(iuranIdNew.getClass(), iuranIdNew.getIuranId());
                pengeluaranUser.setIuranId(iuranIdNew);
            }
            if (transaksiIdNew != null) {
                transaksiIdNew = em.getReference(transaksiIdNew.getClass(), transaksiIdNew.getTransaksiId());
                pengeluaranUser.setTransaksiId(transaksiIdNew);
            }
            pengeluaranUser = em.merge(pengeluaranUser);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getPengeluaranUserList().remove(pengeluaranUser);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getPengeluaranUserList().add(pengeluaranUser);
                userIdNew = em.merge(userIdNew);
            }
            if (iuranIdOld != null && !iuranIdOld.equals(iuranIdNew)) {
                iuranIdOld.getPengeluaranUserList().remove(pengeluaranUser);
                iuranIdOld = em.merge(iuranIdOld);
            }
            if (iuranIdNew != null && !iuranIdNew.equals(iuranIdOld)) {
                iuranIdNew.getPengeluaranUserList().add(pengeluaranUser);
                iuranIdNew = em.merge(iuranIdNew);
            }
            if (transaksiIdOld != null && !transaksiIdOld.equals(transaksiIdNew)) {
                transaksiIdOld.getPengeluaranUserList().remove(pengeluaranUser);
                transaksiIdOld = em.merge(transaksiIdOld);
            }
            if (transaksiIdNew != null && !transaksiIdNew.equals(transaksiIdOld)) {
                transaksiIdNew.getPengeluaranUserList().add(pengeluaranUser);
                transaksiIdNew = em.merge(transaksiIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pengeluaranUser.getPengeluaranUserId();
                if (findPengeluaranUser(id) == null) {
                    throw new NonexistentEntityException("The pengeluaranUser with id " + id + " no longer exists.");
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
            PengeluaranUser pengeluaranUser;
            try {
                pengeluaranUser = em.getReference(PengeluaranUser.class, id);
                pengeluaranUser.getPengeluaranUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pengeluaranUser with id " + id + " no longer exists.", enfe);
            }
            User userId = pengeluaranUser.getUserId();
            if (userId != null) {
                userId.getPengeluaranUserList().remove(pengeluaranUser);
                userId = em.merge(userId);
            }
            Iuran iuranId = pengeluaranUser.getIuranId();
            if (iuranId != null) {
                iuranId.getPengeluaranUserList().remove(pengeluaranUser);
                iuranId = em.merge(iuranId);
            }
            Transaksi transaksiId = pengeluaranUser.getTransaksiId();
            if (transaksiId != null) {
                transaksiId.getPengeluaranUserList().remove(pengeluaranUser);
                transaksiId = em.merge(transaksiId);
            }
            em.remove(pengeluaranUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PengeluaranUser> findPengeluaranUserEntities() {
        return findPengeluaranUserEntities(true, -1, -1);
    }

    public List<PengeluaranUser> findPengeluaranUserEntities(int maxResults, int firstResult) {
        return findPengeluaranUserEntities(false, maxResults, firstResult);
    }

    private List<PengeluaranUser> findPengeluaranUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PengeluaranUser.class));
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

    public PengeluaranUser findPengeluaranUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PengeluaranUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getPengeluaranUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PengeluaranUser> rt = cq.from(PengeluaranUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

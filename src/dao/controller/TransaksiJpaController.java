/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.controller;

import dao.controller.exceptions.IllegalOrphanException;
import dao.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.PengeluaranUser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.IuranUser;
import model.Transaksi;

/**
 *
 * @author fachrul
 */
public class TransaksiJpaController implements Serializable {

    public TransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksi transaksi) {
        if (transaksi.getPengeluaranUserList() == null) {
            transaksi.setPengeluaranUserList(new ArrayList<PengeluaranUser>());
        }
        if (transaksi.getIuranUserList() == null) {
            transaksi.setIuranUserList(new ArrayList<IuranUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PengeluaranUser> attachedPengeluaranUserList = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListPengeluaranUserToAttach : transaksi.getPengeluaranUserList()) {
                pengeluaranUserListPengeluaranUserToAttach = em.getReference(pengeluaranUserListPengeluaranUserToAttach.getClass(), pengeluaranUserListPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserList.add(pengeluaranUserListPengeluaranUserToAttach);
            }
            transaksi.setPengeluaranUserList(attachedPengeluaranUserList);
            List<IuranUser> attachedIuranUserList = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListIuranUserToAttach : transaksi.getIuranUserList()) {
                iuranUserListIuranUserToAttach = em.getReference(iuranUserListIuranUserToAttach.getClass(), iuranUserListIuranUserToAttach.getIuranUserId());
                attachedIuranUserList.add(iuranUserListIuranUserToAttach);
            }
            transaksi.setIuranUserList(attachedIuranUserList);
            em.persist(transaksi);
            for (PengeluaranUser pengeluaranUserListPengeluaranUser : transaksi.getPengeluaranUserList()) {
                Transaksi oldTransaksiIdOfPengeluaranUserListPengeluaranUser = pengeluaranUserListPengeluaranUser.getTransaksiId();
                pengeluaranUserListPengeluaranUser.setTransaksiId(transaksi);
                pengeluaranUserListPengeluaranUser = em.merge(pengeluaranUserListPengeluaranUser);
                if (oldTransaksiIdOfPengeluaranUserListPengeluaranUser != null) {
                    oldTransaksiIdOfPengeluaranUserListPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListPengeluaranUser);
                    oldTransaksiIdOfPengeluaranUserListPengeluaranUser = em.merge(oldTransaksiIdOfPengeluaranUserListPengeluaranUser);
                }
            }
            for (IuranUser iuranUserListIuranUser : transaksi.getIuranUserList()) {
                Transaksi oldTransaksiIdOfIuranUserListIuranUser = iuranUserListIuranUser.getTransaksiId();
                iuranUserListIuranUser.setTransaksiId(transaksi);
                iuranUserListIuranUser = em.merge(iuranUserListIuranUser);
                if (oldTransaksiIdOfIuranUserListIuranUser != null) {
                    oldTransaksiIdOfIuranUserListIuranUser.getIuranUserList().remove(iuranUserListIuranUser);
                    oldTransaksiIdOfIuranUserListIuranUser = em.merge(oldTransaksiIdOfIuranUserListIuranUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksi transaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi persistentTransaksi = em.find(Transaksi.class, transaksi.getTransaksiId());
            List<PengeluaranUser> pengeluaranUserListOld = persistentTransaksi.getPengeluaranUserList();
            List<PengeluaranUser> pengeluaranUserListNew = transaksi.getPengeluaranUserList();
            List<IuranUser> iuranUserListOld = persistentTransaksi.getIuranUserList();
            List<IuranUser> iuranUserListNew = transaksi.getIuranUserList();
            List<String> illegalOrphanMessages = null;
            for (PengeluaranUser pengeluaranUserListOldPengeluaranUser : pengeluaranUserListOld) {
                if (!pengeluaranUserListNew.contains(pengeluaranUserListOldPengeluaranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PengeluaranUser " + pengeluaranUserListOldPengeluaranUser + " since its transaksiId field is not nullable.");
                }
            }
            for (IuranUser iuranUserListOldIuranUser : iuranUserListOld) {
                if (!iuranUserListNew.contains(iuranUserListOldIuranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IuranUser " + iuranUserListOldIuranUser + " since its transaksiId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PengeluaranUser> attachedPengeluaranUserListNew = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUserToAttach : pengeluaranUserListNew) {
                pengeluaranUserListNewPengeluaranUserToAttach = em.getReference(pengeluaranUserListNewPengeluaranUserToAttach.getClass(), pengeluaranUserListNewPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserListNew.add(pengeluaranUserListNewPengeluaranUserToAttach);
            }
            pengeluaranUserListNew = attachedPengeluaranUserListNew;
            transaksi.setPengeluaranUserList(pengeluaranUserListNew);
            List<IuranUser> attachedIuranUserListNew = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListNewIuranUserToAttach : iuranUserListNew) {
                iuranUserListNewIuranUserToAttach = em.getReference(iuranUserListNewIuranUserToAttach.getClass(), iuranUserListNewIuranUserToAttach.getIuranUserId());
                attachedIuranUserListNew.add(iuranUserListNewIuranUserToAttach);
            }
            iuranUserListNew = attachedIuranUserListNew;
            transaksi.setIuranUserList(iuranUserListNew);
            transaksi = em.merge(transaksi);
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUser : pengeluaranUserListNew) {
                if (!pengeluaranUserListOld.contains(pengeluaranUserListNewPengeluaranUser)) {
                    Transaksi oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser = pengeluaranUserListNewPengeluaranUser.getTransaksiId();
                    pengeluaranUserListNewPengeluaranUser.setTransaksiId(transaksi);
                    pengeluaranUserListNewPengeluaranUser = em.merge(pengeluaranUserListNewPengeluaranUser);
                    if (oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser != null && !oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser.equals(transaksi)) {
                        oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListNewPengeluaranUser);
                        oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser = em.merge(oldTransaksiIdOfPengeluaranUserListNewPengeluaranUser);
                    }
                }
            }
            for (IuranUser iuranUserListNewIuranUser : iuranUserListNew) {
                if (!iuranUserListOld.contains(iuranUserListNewIuranUser)) {
                    Transaksi oldTransaksiIdOfIuranUserListNewIuranUser = iuranUserListNewIuranUser.getTransaksiId();
                    iuranUserListNewIuranUser.setTransaksiId(transaksi);
                    iuranUserListNewIuranUser = em.merge(iuranUserListNewIuranUser);
                    if (oldTransaksiIdOfIuranUserListNewIuranUser != null && !oldTransaksiIdOfIuranUserListNewIuranUser.equals(transaksi)) {
                        oldTransaksiIdOfIuranUserListNewIuranUser.getIuranUserList().remove(iuranUserListNewIuranUser);
                        oldTransaksiIdOfIuranUserListNewIuranUser = em.merge(oldTransaksiIdOfIuranUserListNewIuranUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaksi.getTransaksiId();
                if (findTransaksi(id) == null) {
                    throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi;
            try {
                transaksi = em.getReference(Transaksi.class, id);
                transaksi.getTransaksiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PengeluaranUser> pengeluaranUserListOrphanCheck = transaksi.getPengeluaranUserList();
            for (PengeluaranUser pengeluaranUserListOrphanCheckPengeluaranUser : pengeluaranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaksi (" + transaksi + ") cannot be destroyed since the PengeluaranUser " + pengeluaranUserListOrphanCheckPengeluaranUser + " in its pengeluaranUserList field has a non-nullable transaksiId field.");
            }
            List<IuranUser> iuranUserListOrphanCheck = transaksi.getIuranUserList();
            for (IuranUser iuranUserListOrphanCheckIuranUser : iuranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaksi (" + transaksi + ") cannot be destroyed since the IuranUser " + iuranUserListOrphanCheckIuranUser + " in its iuranUserList field has a non-nullable transaksiId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksi> findTransaksiEntities() {
        return findTransaksiEntities(true, -1, -1);
    }

    public List<Transaksi> findTransaksiEntities(int maxResults, int firstResult) {
        return findTransaksiEntities(false, maxResults, firstResult);
    }

    private List<Transaksi> findTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksi.class));
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

    public Transaksi findTransaksi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksi> rt = cq.from(Transaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

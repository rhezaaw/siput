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
import model.Iuran;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.IuranKategori;

/**
 *
 * @author fachrul
 */
public class IuranKategoriJpaController implements Serializable {

    public IuranKategoriJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IuranKategori iuranKategori) {
        if (iuranKategori.getIuranList() == null) {
            iuranKategori.setIuranList(new ArrayList<Iuran>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Iuran> attachedIuranList = new ArrayList<Iuran>();
            for (Iuran iuranListIuranToAttach : iuranKategori.getIuranList()) {
                iuranListIuranToAttach = em.getReference(iuranListIuranToAttach.getClass(), iuranListIuranToAttach.getIuranId());
                attachedIuranList.add(iuranListIuranToAttach);
            }
            iuranKategori.setIuranList(attachedIuranList);
            em.persist(iuranKategori);
            for (Iuran iuranListIuran : iuranKategori.getIuranList()) {
                IuranKategori oldIuranKategoriIdOfIuranListIuran = iuranListIuran.getIuranKategoriId();
                iuranListIuran.setIuranKategoriId(iuranKategori);
                iuranListIuran = em.merge(iuranListIuran);
                if (oldIuranKategoriIdOfIuranListIuran != null) {
                    oldIuranKategoriIdOfIuranListIuran.getIuranList().remove(iuranListIuran);
                    oldIuranKategoriIdOfIuranListIuran = em.merge(oldIuranKategoriIdOfIuranListIuran);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IuranKategori iuranKategori) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IuranKategori persistentIuranKategori = em.find(IuranKategori.class, iuranKategori.getIuranKategoriId());
            List<Iuran> iuranListOld = persistentIuranKategori.getIuranList();
            List<Iuran> iuranListNew = iuranKategori.getIuranList();
            List<String> illegalOrphanMessages = null;
            for (Iuran iuranListOldIuran : iuranListOld) {
                if (!iuranListNew.contains(iuranListOldIuran)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Iuran " + iuranListOldIuran + " since its iuranKategoriId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Iuran> attachedIuranListNew = new ArrayList<Iuran>();
            for (Iuran iuranListNewIuranToAttach : iuranListNew) {
                iuranListNewIuranToAttach = em.getReference(iuranListNewIuranToAttach.getClass(), iuranListNewIuranToAttach.getIuranId());
                attachedIuranListNew.add(iuranListNewIuranToAttach);
            }
            iuranListNew = attachedIuranListNew;
            iuranKategori.setIuranList(iuranListNew);
            iuranKategori = em.merge(iuranKategori);
            for (Iuran iuranListNewIuran : iuranListNew) {
                if (!iuranListOld.contains(iuranListNewIuran)) {
                    IuranKategori oldIuranKategoriIdOfIuranListNewIuran = iuranListNewIuran.getIuranKategoriId();
                    iuranListNewIuran.setIuranKategoriId(iuranKategori);
                    iuranListNewIuran = em.merge(iuranListNewIuran);
                    if (oldIuranKategoriIdOfIuranListNewIuran != null && !oldIuranKategoriIdOfIuranListNewIuran.equals(iuranKategori)) {
                        oldIuranKategoriIdOfIuranListNewIuran.getIuranList().remove(iuranListNewIuran);
                        oldIuranKategoriIdOfIuranListNewIuran = em.merge(oldIuranKategoriIdOfIuranListNewIuran);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iuranKategori.getIuranKategoriId();
                if (findIuranKategori(id) == null) {
                    throw new NonexistentEntityException("The iuranKategori with id " + id + " no longer exists.");
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
            IuranKategori iuranKategori;
            try {
                iuranKategori = em.getReference(IuranKategori.class, id);
                iuranKategori.getIuranKategoriId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iuranKategori with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Iuran> iuranListOrphanCheck = iuranKategori.getIuranList();
            for (Iuran iuranListOrphanCheckIuran : iuranListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IuranKategori (" + iuranKategori + ") cannot be destroyed since the Iuran " + iuranListOrphanCheckIuran + " in its iuranList field has a non-nullable iuranKategoriId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(iuranKategori);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IuranKategori> findIuranKategoriEntities() {
        return findIuranKategoriEntities(true, -1, -1);
    }

    public List<IuranKategori> findIuranKategoriEntities(int maxResults, int firstResult) {
        return findIuranKategoriEntities(false, maxResults, firstResult);
    }

    private List<IuranKategori> findIuranKategoriEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IuranKategori.class));
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

    public IuranKategori findIuranKategori(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IuranKategori.class, id);
        } finally {
            em.close();
        }
    }

    public int getIuranKategoriCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IuranKategori> rt = cq.from(IuranKategori.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

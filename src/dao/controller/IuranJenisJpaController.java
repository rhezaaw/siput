/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.controller;

import dao.controller.exceptions.IllegalOrphanException;
import dao.controller.exceptions.NonexistentEntityException;
import dao.controller.exceptions.PreexistingEntityException;
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
import model.IuranJenis;

/**
 *
 * @author fachrul
 */
public class IuranJenisJpaController implements Serializable {

    public IuranJenisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IuranJenis iuranJenis) throws PreexistingEntityException, Exception {
        if (iuranJenis.getIuranList() == null) {
            iuranJenis.setIuranList(new ArrayList<Iuran>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Iuran> attachedIuranList = new ArrayList<Iuran>();
            for (Iuran iuranListIuranToAttach : iuranJenis.getIuranList()) {
                iuranListIuranToAttach = em.getReference(iuranListIuranToAttach.getClass(), iuranListIuranToAttach.getIuranId());
                attachedIuranList.add(iuranListIuranToAttach);
            }
            iuranJenis.setIuranList(attachedIuranList);
            em.persist(iuranJenis);
            for (Iuran iuranListIuran : iuranJenis.getIuranList()) {
                IuranJenis oldIuranJenisIdOfIuranListIuran = iuranListIuran.getIuranJenisId();
                iuranListIuran.setIuranJenisId(iuranJenis);
                iuranListIuran = em.merge(iuranListIuran);
                if (oldIuranJenisIdOfIuranListIuran != null) {
                    oldIuranJenisIdOfIuranListIuran.getIuranList().remove(iuranListIuran);
                    oldIuranJenisIdOfIuranListIuran = em.merge(oldIuranJenisIdOfIuranListIuran);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIuranJenis(iuranJenis.getIuranJenisId()) != null) {
                throw new PreexistingEntityException("IuranJenis " + iuranJenis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IuranJenis iuranJenis) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IuranJenis persistentIuranJenis = em.find(IuranJenis.class, iuranJenis.getIuranJenisId());
            List<Iuran> iuranListOld = persistentIuranJenis.getIuranList();
            List<Iuran> iuranListNew = iuranJenis.getIuranList();
            List<String> illegalOrphanMessages = null;
            for (Iuran iuranListOldIuran : iuranListOld) {
                if (!iuranListNew.contains(iuranListOldIuran)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Iuran " + iuranListOldIuran + " since its iuranJenisId field is not nullable.");
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
            iuranJenis.setIuranList(iuranListNew);
            iuranJenis = em.merge(iuranJenis);
            for (Iuran iuranListNewIuran : iuranListNew) {
                if (!iuranListOld.contains(iuranListNewIuran)) {
                    IuranJenis oldIuranJenisIdOfIuranListNewIuran = iuranListNewIuran.getIuranJenisId();
                    iuranListNewIuran.setIuranJenisId(iuranJenis);
                    iuranListNewIuran = em.merge(iuranListNewIuran);
                    if (oldIuranJenisIdOfIuranListNewIuran != null && !oldIuranJenisIdOfIuranListNewIuran.equals(iuranJenis)) {
                        oldIuranJenisIdOfIuranListNewIuran.getIuranList().remove(iuranListNewIuran);
                        oldIuranJenisIdOfIuranListNewIuran = em.merge(oldIuranJenisIdOfIuranListNewIuran);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iuranJenis.getIuranJenisId();
                if (findIuranJenis(id) == null) {
                    throw new NonexistentEntityException("The iuranJenis with id " + id + " no longer exists.");
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
            IuranJenis iuranJenis;
            try {
                iuranJenis = em.getReference(IuranJenis.class, id);
                iuranJenis.getIuranJenisId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iuranJenis with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Iuran> iuranListOrphanCheck = iuranJenis.getIuranList();
            for (Iuran iuranListOrphanCheckIuran : iuranListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IuranJenis (" + iuranJenis + ") cannot be destroyed since the Iuran " + iuranListOrphanCheckIuran + " in its iuranList field has a non-nullable iuranJenisId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(iuranJenis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IuranJenis> findIuranJenisEntities() {
        return findIuranJenisEntities(true, -1, -1);
    }

    public List<IuranJenis> findIuranJenisEntities(int maxResults, int firstResult) {
        return findIuranJenisEntities(false, maxResults, firstResult);
    }

    private List<IuranJenis> findIuranJenisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IuranJenis.class));
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

    public IuranJenis findIuranJenis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IuranJenis.class, id);
        } finally {
            em.close();
        }
    }

    public int getIuranJenisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IuranJenis> rt = cq.from(IuranJenis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import model.Pengeluaran;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.PengeluaranJenis;

/**
 *
 * @author fachrul
 */
public class PengeluaranJenisJpaController implements Serializable {

    public PengeluaranJenisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PengeluaranJenis pengeluaranJenis) {
        if (pengeluaranJenis.getPengeluaranList() == null) {
            pengeluaranJenis.setPengeluaranList(new ArrayList<Pengeluaran>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pengeluaran> attachedPengeluaranList = new ArrayList<Pengeluaran>();
            for (Pengeluaran pengeluaranListPengeluaranToAttach : pengeluaranJenis.getPengeluaranList()) {
                pengeluaranListPengeluaranToAttach = em.getReference(pengeluaranListPengeluaranToAttach.getClass(), pengeluaranListPengeluaranToAttach.getPengeluaranId());
                attachedPengeluaranList.add(pengeluaranListPengeluaranToAttach);
            }
            pengeluaranJenis.setPengeluaranList(attachedPengeluaranList);
            em.persist(pengeluaranJenis);
            for (Pengeluaran pengeluaranListPengeluaran : pengeluaranJenis.getPengeluaranList()) {
                PengeluaranJenis oldPengeluaranJenisIdOfPengeluaranListPengeluaran = pengeluaranListPengeluaran.getPengeluaranJenisId();
                pengeluaranListPengeluaran.setPengeluaranJenisId(pengeluaranJenis);
                pengeluaranListPengeluaran = em.merge(pengeluaranListPengeluaran);
                if (oldPengeluaranJenisIdOfPengeluaranListPengeluaran != null) {
                    oldPengeluaranJenisIdOfPengeluaranListPengeluaran.getPengeluaranList().remove(pengeluaranListPengeluaran);
                    oldPengeluaranJenisIdOfPengeluaranListPengeluaran = em.merge(oldPengeluaranJenisIdOfPengeluaranListPengeluaran);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PengeluaranJenis pengeluaranJenis) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PengeluaranJenis persistentPengeluaranJenis = em.find(PengeluaranJenis.class, pengeluaranJenis.getPengeluaranJenisId());
            List<Pengeluaran> pengeluaranListOld = persistentPengeluaranJenis.getPengeluaranList();
            List<Pengeluaran> pengeluaranListNew = pengeluaranJenis.getPengeluaranList();
            List<String> illegalOrphanMessages = null;
            for (Pengeluaran pengeluaranListOldPengeluaran : pengeluaranListOld) {
                if (!pengeluaranListNew.contains(pengeluaranListOldPengeluaran)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pengeluaran " + pengeluaranListOldPengeluaran + " since its pengeluaranJenisId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pengeluaran> attachedPengeluaranListNew = new ArrayList<Pengeluaran>();
            for (Pengeluaran pengeluaranListNewPengeluaranToAttach : pengeluaranListNew) {
                pengeluaranListNewPengeluaranToAttach = em.getReference(pengeluaranListNewPengeluaranToAttach.getClass(), pengeluaranListNewPengeluaranToAttach.getPengeluaranId());
                attachedPengeluaranListNew.add(pengeluaranListNewPengeluaranToAttach);
            }
            pengeluaranListNew = attachedPengeluaranListNew;
            pengeluaranJenis.setPengeluaranList(pengeluaranListNew);
            pengeluaranJenis = em.merge(pengeluaranJenis);
            for (Pengeluaran pengeluaranListNewPengeluaran : pengeluaranListNew) {
                if (!pengeluaranListOld.contains(pengeluaranListNewPengeluaran)) {
                    PengeluaranJenis oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran = pengeluaranListNewPengeluaran.getPengeluaranJenisId();
                    pengeluaranListNewPengeluaran.setPengeluaranJenisId(pengeluaranJenis);
                    pengeluaranListNewPengeluaran = em.merge(pengeluaranListNewPengeluaran);
                    if (oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran != null && !oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran.equals(pengeluaranJenis)) {
                        oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran.getPengeluaranList().remove(pengeluaranListNewPengeluaran);
                        oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran = em.merge(oldPengeluaranJenisIdOfPengeluaranListNewPengeluaran);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pengeluaranJenis.getPengeluaranJenisId();
                if (findPengeluaranJenis(id) == null) {
                    throw new NonexistentEntityException("The pengeluaranJenis with id " + id + " no longer exists.");
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
            PengeluaranJenis pengeluaranJenis;
            try {
                pengeluaranJenis = em.getReference(PengeluaranJenis.class, id);
                pengeluaranJenis.getPengeluaranJenisId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pengeluaranJenis with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pengeluaran> pengeluaranListOrphanCheck = pengeluaranJenis.getPengeluaranList();
            for (Pengeluaran pengeluaranListOrphanCheckPengeluaran : pengeluaranListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PengeluaranJenis (" + pengeluaranJenis + ") cannot be destroyed since the Pengeluaran " + pengeluaranListOrphanCheckPengeluaran + " in its pengeluaranList field has a non-nullable pengeluaranJenisId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pengeluaranJenis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PengeluaranJenis> findPengeluaranJenisEntities() {
        return findPengeluaranJenisEntities(true, -1, -1);
    }

    public List<PengeluaranJenis> findPengeluaranJenisEntities(int maxResults, int firstResult) {
        return findPengeluaranJenisEntities(false, maxResults, firstResult);
    }

    private List<PengeluaranJenis> findPengeluaranJenisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PengeluaranJenis.class));
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

    public PengeluaranJenis findPengeluaranJenis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PengeluaranJenis.class, id);
        } finally {
            em.close();
        }
    }

    public int getPengeluaranJenisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PengeluaranJenis> rt = cq.from(PengeluaranJenis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

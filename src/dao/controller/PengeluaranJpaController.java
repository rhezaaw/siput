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
import model.PengeluaranJenis;
import model.PengeluaranPerubahan;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Pengeluaran;

/**
 *
 * @author fachrul
 */
public class PengeluaranJpaController implements Serializable {

    public PengeluaranJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pengeluaran pengeluaran) {
        if (pengeluaran.getPengeluaranPerubahanList() == null) {
            pengeluaran.setPengeluaranPerubahanList(new ArrayList<PengeluaranPerubahan>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PengeluaranJenis pengeluaranJenisId = pengeluaran.getPengeluaranJenisId();
            if (pengeluaranJenisId != null) {
                pengeluaranJenisId = em.getReference(pengeluaranJenisId.getClass(), pengeluaranJenisId.getPengeluaranJenisId());
                pengeluaran.setPengeluaranJenisId(pengeluaranJenisId);
            }
            List<PengeluaranPerubahan> attachedPengeluaranPerubahanList = new ArrayList<PengeluaranPerubahan>();
            for (PengeluaranPerubahan pengeluaranPerubahanListPengeluaranPerubahanToAttach : pengeluaran.getPengeluaranPerubahanList()) {
                pengeluaranPerubahanListPengeluaranPerubahanToAttach = em.getReference(pengeluaranPerubahanListPengeluaranPerubahanToAttach.getClass(), pengeluaranPerubahanListPengeluaranPerubahanToAttach.getPengeluaranPerubahanId());
                attachedPengeluaranPerubahanList.add(pengeluaranPerubahanListPengeluaranPerubahanToAttach);
            }
            pengeluaran.setPengeluaranPerubahanList(attachedPengeluaranPerubahanList);
            em.persist(pengeluaran);
            if (pengeluaranJenisId != null) {
                pengeluaranJenisId.getPengeluaranList().add(pengeluaran);
                pengeluaranJenisId = em.merge(pengeluaranJenisId);
            }
            for (PengeluaranPerubahan pengeluaranPerubahanListPengeluaranPerubahan : pengeluaran.getPengeluaranPerubahanList()) {
                Pengeluaran oldPengeluaranIdOfPengeluaranPerubahanListPengeluaranPerubahan = pengeluaranPerubahanListPengeluaranPerubahan.getPengeluaranId();
                pengeluaranPerubahanListPengeluaranPerubahan.setPengeluaranId(pengeluaran);
                pengeluaranPerubahanListPengeluaranPerubahan = em.merge(pengeluaranPerubahanListPengeluaranPerubahan);
                if (oldPengeluaranIdOfPengeluaranPerubahanListPengeluaranPerubahan != null) {
                    oldPengeluaranIdOfPengeluaranPerubahanListPengeluaranPerubahan.getPengeluaranPerubahanList().remove(pengeluaranPerubahanListPengeluaranPerubahan);
                    oldPengeluaranIdOfPengeluaranPerubahanListPengeluaranPerubahan = em.merge(oldPengeluaranIdOfPengeluaranPerubahanListPengeluaranPerubahan);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pengeluaran pengeluaran) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pengeluaran persistentPengeluaran = em.find(Pengeluaran.class, pengeluaran.getPengeluaranId());
            PengeluaranJenis pengeluaranJenisIdOld = persistentPengeluaran.getPengeluaranJenisId();
            PengeluaranJenis pengeluaranJenisIdNew = pengeluaran.getPengeluaranJenisId();
            List<PengeluaranPerubahan> pengeluaranPerubahanListOld = persistentPengeluaran.getPengeluaranPerubahanList();
            List<PengeluaranPerubahan> pengeluaranPerubahanListNew = pengeluaran.getPengeluaranPerubahanList();
            List<String> illegalOrphanMessages = null;
            for (PengeluaranPerubahan pengeluaranPerubahanListOldPengeluaranPerubahan : pengeluaranPerubahanListOld) {
                if (!pengeluaranPerubahanListNew.contains(pengeluaranPerubahanListOldPengeluaranPerubahan)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PengeluaranPerubahan " + pengeluaranPerubahanListOldPengeluaranPerubahan + " since its pengeluaranId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pengeluaranJenisIdNew != null) {
                pengeluaranJenisIdNew = em.getReference(pengeluaranJenisIdNew.getClass(), pengeluaranJenisIdNew.getPengeluaranJenisId());
                pengeluaran.setPengeluaranJenisId(pengeluaranJenisIdNew);
            }
            List<PengeluaranPerubahan> attachedPengeluaranPerubahanListNew = new ArrayList<PengeluaranPerubahan>();
            for (PengeluaranPerubahan pengeluaranPerubahanListNewPengeluaranPerubahanToAttach : pengeluaranPerubahanListNew) {
                pengeluaranPerubahanListNewPengeluaranPerubahanToAttach = em.getReference(pengeluaranPerubahanListNewPengeluaranPerubahanToAttach.getClass(), pengeluaranPerubahanListNewPengeluaranPerubahanToAttach.getPengeluaranPerubahanId());
                attachedPengeluaranPerubahanListNew.add(pengeluaranPerubahanListNewPengeluaranPerubahanToAttach);
            }
            pengeluaranPerubahanListNew = attachedPengeluaranPerubahanListNew;
            pengeluaran.setPengeluaranPerubahanList(pengeluaranPerubahanListNew);
            pengeluaran = em.merge(pengeluaran);
            if (pengeluaranJenisIdOld != null && !pengeluaranJenisIdOld.equals(pengeluaranJenisIdNew)) {
                pengeluaranJenisIdOld.getPengeluaranList().remove(pengeluaran);
                pengeluaranJenisIdOld = em.merge(pengeluaranJenisIdOld);
            }
            if (pengeluaranJenisIdNew != null && !pengeluaranJenisIdNew.equals(pengeluaranJenisIdOld)) {
                pengeluaranJenisIdNew.getPengeluaranList().add(pengeluaran);
                pengeluaranJenisIdNew = em.merge(pengeluaranJenisIdNew);
            }
            for (PengeluaranPerubahan pengeluaranPerubahanListNewPengeluaranPerubahan : pengeluaranPerubahanListNew) {
                if (!pengeluaranPerubahanListOld.contains(pengeluaranPerubahanListNewPengeluaranPerubahan)) {
                    Pengeluaran oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan = pengeluaranPerubahanListNewPengeluaranPerubahan.getPengeluaranId();
                    pengeluaranPerubahanListNewPengeluaranPerubahan.setPengeluaranId(pengeluaran);
                    pengeluaranPerubahanListNewPengeluaranPerubahan = em.merge(pengeluaranPerubahanListNewPengeluaranPerubahan);
                    if (oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan != null && !oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan.equals(pengeluaran)) {
                        oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan.getPengeluaranPerubahanList().remove(pengeluaranPerubahanListNewPengeluaranPerubahan);
                        oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan = em.merge(oldPengeluaranIdOfPengeluaranPerubahanListNewPengeluaranPerubahan);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pengeluaran.getPengeluaranId();
                if (findPengeluaran(id) == null) {
                    throw new NonexistentEntityException("The pengeluaran with id " + id + " no longer exists.");
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
            Pengeluaran pengeluaran;
            try {
                pengeluaran = em.getReference(Pengeluaran.class, id);
                pengeluaran.getPengeluaranId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pengeluaran with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PengeluaranPerubahan> pengeluaranPerubahanListOrphanCheck = pengeluaran.getPengeluaranPerubahanList();
            for (PengeluaranPerubahan pengeluaranPerubahanListOrphanCheckPengeluaranPerubahan : pengeluaranPerubahanListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pengeluaran (" + pengeluaran + ") cannot be destroyed since the PengeluaranPerubahan " + pengeluaranPerubahanListOrphanCheckPengeluaranPerubahan + " in its pengeluaranPerubahanList field has a non-nullable pengeluaranId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PengeluaranJenis pengeluaranJenisId = pengeluaran.getPengeluaranJenisId();
            if (pengeluaranJenisId != null) {
                pengeluaranJenisId.getPengeluaranList().remove(pengeluaran);
                pengeluaranJenisId = em.merge(pengeluaranJenisId);
            }
            em.remove(pengeluaran);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pengeluaran> findPengeluaranEntities() {
        return findPengeluaranEntities(true, -1, -1);
    }

    public List<Pengeluaran> findPengeluaranEntities(int maxResults, int firstResult) {
        return findPengeluaranEntities(false, maxResults, firstResult);
    }

    private List<Pengeluaran> findPengeluaranEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pengeluaran.class));
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

    public Pengeluaran findPengeluaran(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pengeluaran.class, id);
        } finally {
            em.close();
        }
    }

    public int getPengeluaranCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pengeluaran> rt = cq.from(Pengeluaran.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

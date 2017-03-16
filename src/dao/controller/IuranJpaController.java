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
import model.IuranKategori;
import model.IuranJenis;
import model.IuranPerubahan;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Iuran;
import model.PengeluaranUser;
import model.IuranUser;

/**
 *
 * @author fachrul
 */
public class IuranJpaController implements Serializable {

    public IuranJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Iuran iuran) {
        if (iuran.getIuranPerubahanList() == null) {
            iuran.setIuranPerubahanList(new ArrayList<IuranPerubahan>());
        }
        if (iuran.getPengeluaranUserList() == null) {
            iuran.setPengeluaranUserList(new ArrayList<PengeluaranUser>());
        }
        if (iuran.getIuranUserList() == null) {
            iuran.setIuranUserList(new ArrayList<IuranUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IuranKategori iuranKategoriId = iuran.getIuranKategoriId();
            if (iuranKategoriId != null) {
                iuranKategoriId = em.getReference(iuranKategoriId.getClass(), iuranKategoriId.getIuranKategoriId());
                iuran.setIuranKategoriId(iuranKategoriId);
            }
            IuranJenis iuranJenisId = iuran.getIuranJenisId();
            if (iuranJenisId != null) {
                iuranJenisId = em.getReference(iuranJenisId.getClass(), iuranJenisId.getIuranJenisId());
                iuran.setIuranJenisId(iuranJenisId);
            }
            List<IuranPerubahan> attachedIuranPerubahanList = new ArrayList<IuranPerubahan>();
            for (IuranPerubahan iuranPerubahanListIuranPerubahanToAttach : iuran.getIuranPerubahanList()) {
                iuranPerubahanListIuranPerubahanToAttach = em.getReference(iuranPerubahanListIuranPerubahanToAttach.getClass(), iuranPerubahanListIuranPerubahanToAttach.getIuranPerubahanId());
                attachedIuranPerubahanList.add(iuranPerubahanListIuranPerubahanToAttach);
            }
            iuran.setIuranPerubahanList(attachedIuranPerubahanList);
            List<PengeluaranUser> attachedPengeluaranUserList = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListPengeluaranUserToAttach : iuran.getPengeluaranUserList()) {
                pengeluaranUserListPengeluaranUserToAttach = em.getReference(pengeluaranUserListPengeluaranUserToAttach.getClass(), pengeluaranUserListPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserList.add(pengeluaranUserListPengeluaranUserToAttach);
            }
            iuran.setPengeluaranUserList(attachedPengeluaranUserList);
            List<IuranUser> attachedIuranUserList = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListIuranUserToAttach : iuran.getIuranUserList()) {
                iuranUserListIuranUserToAttach = em.getReference(iuranUserListIuranUserToAttach.getClass(), iuranUserListIuranUserToAttach.getIuranUserId());
                attachedIuranUserList.add(iuranUserListIuranUserToAttach);
            }
            iuran.setIuranUserList(attachedIuranUserList);
            em.persist(iuran);
            if (iuranKategoriId != null) {
                iuranKategoriId.getIuranList().add(iuran);
                iuranKategoriId = em.merge(iuranKategoriId);
            }
            if (iuranJenisId != null) {
                iuranJenisId.getIuranList().add(iuran);
                iuranJenisId = em.merge(iuranJenisId);
            }
            for (IuranPerubahan iuranPerubahanListIuranPerubahan : iuran.getIuranPerubahanList()) {
                Iuran oldIuranIdOfIuranPerubahanListIuranPerubahan = iuranPerubahanListIuranPerubahan.getIuranId();
                iuranPerubahanListIuranPerubahan.setIuranId(iuran);
                iuranPerubahanListIuranPerubahan = em.merge(iuranPerubahanListIuranPerubahan);
                if (oldIuranIdOfIuranPerubahanListIuranPerubahan != null) {
                    oldIuranIdOfIuranPerubahanListIuranPerubahan.getIuranPerubahanList().remove(iuranPerubahanListIuranPerubahan);
                    oldIuranIdOfIuranPerubahanListIuranPerubahan = em.merge(oldIuranIdOfIuranPerubahanListIuranPerubahan);
                }
            }
            for (PengeluaranUser pengeluaranUserListPengeluaranUser : iuran.getPengeluaranUserList()) {
                Iuran oldIuranIdOfPengeluaranUserListPengeluaranUser = pengeluaranUserListPengeluaranUser.getIuranId();
                pengeluaranUserListPengeluaranUser.setIuranId(iuran);
                pengeluaranUserListPengeluaranUser = em.merge(pengeluaranUserListPengeluaranUser);
                if (oldIuranIdOfPengeluaranUserListPengeluaranUser != null) {
                    oldIuranIdOfPengeluaranUserListPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListPengeluaranUser);
                    oldIuranIdOfPengeluaranUserListPengeluaranUser = em.merge(oldIuranIdOfPengeluaranUserListPengeluaranUser);
                }
            }
            for (IuranUser iuranUserListIuranUser : iuran.getIuranUserList()) {
                Iuran oldIuranIdOfIuranUserListIuranUser = iuranUserListIuranUser.getIuranId();
                iuranUserListIuranUser.setIuranId(iuran);
                iuranUserListIuranUser = em.merge(iuranUserListIuranUser);
                if (oldIuranIdOfIuranUserListIuranUser != null) {
                    oldIuranIdOfIuranUserListIuranUser.getIuranUserList().remove(iuranUserListIuranUser);
                    oldIuranIdOfIuranUserListIuranUser = em.merge(oldIuranIdOfIuranUserListIuranUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Iuran iuran) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iuran persistentIuran = em.find(Iuran.class, iuran.getIuranId());
            IuranKategori iuranKategoriIdOld = persistentIuran.getIuranKategoriId();
            IuranKategori iuranKategoriIdNew = iuran.getIuranKategoriId();
            IuranJenis iuranJenisIdOld = persistentIuran.getIuranJenisId();
            IuranJenis iuranJenisIdNew = iuran.getIuranJenisId();
            List<IuranPerubahan> iuranPerubahanListOld = persistentIuran.getIuranPerubahanList();
            List<IuranPerubahan> iuranPerubahanListNew = iuran.getIuranPerubahanList();
            List<PengeluaranUser> pengeluaranUserListOld = persistentIuran.getPengeluaranUserList();
            List<PengeluaranUser> pengeluaranUserListNew = iuran.getPengeluaranUserList();
            List<IuranUser> iuranUserListOld = persistentIuran.getIuranUserList();
            List<IuranUser> iuranUserListNew = iuran.getIuranUserList();
            List<String> illegalOrphanMessages = null;
            for (IuranPerubahan iuranPerubahanListOldIuranPerubahan : iuranPerubahanListOld) {
                if (!iuranPerubahanListNew.contains(iuranPerubahanListOldIuranPerubahan)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IuranPerubahan " + iuranPerubahanListOldIuranPerubahan + " since its iuranId field is not nullable.");
                }
            }
            for (PengeluaranUser pengeluaranUserListOldPengeluaranUser : pengeluaranUserListOld) {
                if (!pengeluaranUserListNew.contains(pengeluaranUserListOldPengeluaranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PengeluaranUser " + pengeluaranUserListOldPengeluaranUser + " since its iuranId field is not nullable.");
                }
            }
            for (IuranUser iuranUserListOldIuranUser : iuranUserListOld) {
                if (!iuranUserListNew.contains(iuranUserListOldIuranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IuranUser " + iuranUserListOldIuranUser + " since its iuranId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (iuranKategoriIdNew != null) {
                iuranKategoriIdNew = em.getReference(iuranKategoriIdNew.getClass(), iuranKategoriIdNew.getIuranKategoriId());
                iuran.setIuranKategoriId(iuranKategoriIdNew);
            }
            if (iuranJenisIdNew != null) {
                iuranJenisIdNew = em.getReference(iuranJenisIdNew.getClass(), iuranJenisIdNew.getIuranJenisId());
                iuran.setIuranJenisId(iuranJenisIdNew);
            }
            List<IuranPerubahan> attachedIuranPerubahanListNew = new ArrayList<IuranPerubahan>();
            for (IuranPerubahan iuranPerubahanListNewIuranPerubahanToAttach : iuranPerubahanListNew) {
                iuranPerubahanListNewIuranPerubahanToAttach = em.getReference(iuranPerubahanListNewIuranPerubahanToAttach.getClass(), iuranPerubahanListNewIuranPerubahanToAttach.getIuranPerubahanId());
                attachedIuranPerubahanListNew.add(iuranPerubahanListNewIuranPerubahanToAttach);
            }
            iuranPerubahanListNew = attachedIuranPerubahanListNew;
            iuran.setIuranPerubahanList(iuranPerubahanListNew);
            List<PengeluaranUser> attachedPengeluaranUserListNew = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUserToAttach : pengeluaranUserListNew) {
                pengeluaranUserListNewPengeluaranUserToAttach = em.getReference(pengeluaranUserListNewPengeluaranUserToAttach.getClass(), pengeluaranUserListNewPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserListNew.add(pengeluaranUserListNewPengeluaranUserToAttach);
            }
            pengeluaranUserListNew = attachedPengeluaranUserListNew;
            iuran.setPengeluaranUserList(pengeluaranUserListNew);
            List<IuranUser> attachedIuranUserListNew = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListNewIuranUserToAttach : iuranUserListNew) {
                iuranUserListNewIuranUserToAttach = em.getReference(iuranUserListNewIuranUserToAttach.getClass(), iuranUserListNewIuranUserToAttach.getIuranUserId());
                attachedIuranUserListNew.add(iuranUserListNewIuranUserToAttach);
            }
            iuranUserListNew = attachedIuranUserListNew;
            iuran.setIuranUserList(iuranUserListNew);
            iuran = em.merge(iuran);
            if (iuranKategoriIdOld != null && !iuranKategoriIdOld.equals(iuranKategoriIdNew)) {
                iuranKategoriIdOld.getIuranList().remove(iuran);
                iuranKategoriIdOld = em.merge(iuranKategoriIdOld);
            }
            if (iuranKategoriIdNew != null && !iuranKategoriIdNew.equals(iuranKategoriIdOld)) {
                iuranKategoriIdNew.getIuranList().add(iuran);
                iuranKategoriIdNew = em.merge(iuranKategoriIdNew);
            }
            if (iuranJenisIdOld != null && !iuranJenisIdOld.equals(iuranJenisIdNew)) {
                iuranJenisIdOld.getIuranList().remove(iuran);
                iuranJenisIdOld = em.merge(iuranJenisIdOld);
            }
            if (iuranJenisIdNew != null && !iuranJenisIdNew.equals(iuranJenisIdOld)) {
                iuranJenisIdNew.getIuranList().add(iuran);
                iuranJenisIdNew = em.merge(iuranJenisIdNew);
            }
            for (IuranPerubahan iuranPerubahanListNewIuranPerubahan : iuranPerubahanListNew) {
                if (!iuranPerubahanListOld.contains(iuranPerubahanListNewIuranPerubahan)) {
                    Iuran oldIuranIdOfIuranPerubahanListNewIuranPerubahan = iuranPerubahanListNewIuranPerubahan.getIuranId();
                    iuranPerubahanListNewIuranPerubahan.setIuranId(iuran);
                    iuranPerubahanListNewIuranPerubahan = em.merge(iuranPerubahanListNewIuranPerubahan);
                    if (oldIuranIdOfIuranPerubahanListNewIuranPerubahan != null && !oldIuranIdOfIuranPerubahanListNewIuranPerubahan.equals(iuran)) {
                        oldIuranIdOfIuranPerubahanListNewIuranPerubahan.getIuranPerubahanList().remove(iuranPerubahanListNewIuranPerubahan);
                        oldIuranIdOfIuranPerubahanListNewIuranPerubahan = em.merge(oldIuranIdOfIuranPerubahanListNewIuranPerubahan);
                    }
                }
            }
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUser : pengeluaranUserListNew) {
                if (!pengeluaranUserListOld.contains(pengeluaranUserListNewPengeluaranUser)) {
                    Iuran oldIuranIdOfPengeluaranUserListNewPengeluaranUser = pengeluaranUserListNewPengeluaranUser.getIuranId();
                    pengeluaranUserListNewPengeluaranUser.setIuranId(iuran);
                    pengeluaranUserListNewPengeluaranUser = em.merge(pengeluaranUserListNewPengeluaranUser);
                    if (oldIuranIdOfPengeluaranUserListNewPengeluaranUser != null && !oldIuranIdOfPengeluaranUserListNewPengeluaranUser.equals(iuran)) {
                        oldIuranIdOfPengeluaranUserListNewPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListNewPengeluaranUser);
                        oldIuranIdOfPengeluaranUserListNewPengeluaranUser = em.merge(oldIuranIdOfPengeluaranUserListNewPengeluaranUser);
                    }
                }
            }
            for (IuranUser iuranUserListNewIuranUser : iuranUserListNew) {
                if (!iuranUserListOld.contains(iuranUserListNewIuranUser)) {
                    Iuran oldIuranIdOfIuranUserListNewIuranUser = iuranUserListNewIuranUser.getIuranId();
                    iuranUserListNewIuranUser.setIuranId(iuran);
                    iuranUserListNewIuranUser = em.merge(iuranUserListNewIuranUser);
                    if (oldIuranIdOfIuranUserListNewIuranUser != null && !oldIuranIdOfIuranUserListNewIuranUser.equals(iuran)) {
                        oldIuranIdOfIuranUserListNewIuranUser.getIuranUserList().remove(iuranUserListNewIuranUser);
                        oldIuranIdOfIuranUserListNewIuranUser = em.merge(oldIuranIdOfIuranUserListNewIuranUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iuran.getIuranId();
                if (findIuran(id) == null) {
                    throw new NonexistentEntityException("The iuran with id " + id + " no longer exists.");
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
            Iuran iuran;
            try {
                iuran = em.getReference(Iuran.class, id);
                iuran.getIuranId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iuran with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<IuranPerubahan> iuranPerubahanListOrphanCheck = iuran.getIuranPerubahanList();
            for (IuranPerubahan iuranPerubahanListOrphanCheckIuranPerubahan : iuranPerubahanListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Iuran (" + iuran + ") cannot be destroyed since the IuranPerubahan " + iuranPerubahanListOrphanCheckIuranPerubahan + " in its iuranPerubahanList field has a non-nullable iuranId field.");
            }
            List<PengeluaranUser> pengeluaranUserListOrphanCheck = iuran.getPengeluaranUserList();
            for (PengeluaranUser pengeluaranUserListOrphanCheckPengeluaranUser : pengeluaranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Iuran (" + iuran + ") cannot be destroyed since the PengeluaranUser " + pengeluaranUserListOrphanCheckPengeluaranUser + " in its pengeluaranUserList field has a non-nullable iuranId field.");
            }
            List<IuranUser> iuranUserListOrphanCheck = iuran.getIuranUserList();
            for (IuranUser iuranUserListOrphanCheckIuranUser : iuranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Iuran (" + iuran + ") cannot be destroyed since the IuranUser " + iuranUserListOrphanCheckIuranUser + " in its iuranUserList field has a non-nullable iuranId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            IuranKategori iuranKategoriId = iuran.getIuranKategoriId();
            if (iuranKategoriId != null) {
                iuranKategoriId.getIuranList().remove(iuran);
                iuranKategoriId = em.merge(iuranKategoriId);
            }
            IuranJenis iuranJenisId = iuran.getIuranJenisId();
            if (iuranJenisId != null) {
                iuranJenisId.getIuranList().remove(iuran);
                iuranJenisId = em.merge(iuranJenisId);
            }
            em.remove(iuran);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Iuran> findIuranEntities() {
        return findIuranEntities(true, -1, -1);
    }

    public List<Iuran> findIuranEntities(int maxResults, int firstResult) {
        return findIuranEntities(false, maxResults, firstResult);
    }

    private List<Iuran> findIuranEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Iuran.class));
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

    public Iuran findIuran(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Iuran.class, id);
        } finally {
            em.close();
        }
    }

    public int getIuranCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Iuran> rt = cq.from(Iuran.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

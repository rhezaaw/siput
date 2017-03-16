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
import model.User;
import model.DepositPerubahan;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Deposit;

/**
 *
 * @author fachrul
 */
public class DepositJpaController implements Serializable {

    public DepositJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deposit deposit) {
        if (deposit.getDepositPerubahanList() == null) {
            deposit.setDepositPerubahanList(new ArrayList<DepositPerubahan>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = deposit.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                deposit.setUserId(userId);
            }
            List<DepositPerubahan> attachedDepositPerubahanList = new ArrayList<DepositPerubahan>();
            for (DepositPerubahan depositPerubahanListDepositPerubahanToAttach : deposit.getDepositPerubahanList()) {
                depositPerubahanListDepositPerubahanToAttach = em.getReference(depositPerubahanListDepositPerubahanToAttach.getClass(), depositPerubahanListDepositPerubahanToAttach.getDepositPerubahanId());
                attachedDepositPerubahanList.add(depositPerubahanListDepositPerubahanToAttach);
            }
            deposit.setDepositPerubahanList(attachedDepositPerubahanList);
            em.persist(deposit);
            if (userId != null) {
                userId.getDepositList().add(deposit);
                userId = em.merge(userId);
            }
            for (DepositPerubahan depositPerubahanListDepositPerubahan : deposit.getDepositPerubahanList()) {
                Deposit oldDepositIdOfDepositPerubahanListDepositPerubahan = depositPerubahanListDepositPerubahan.getDepositId();
                depositPerubahanListDepositPerubahan.setDepositId(deposit);
                depositPerubahanListDepositPerubahan = em.merge(depositPerubahanListDepositPerubahan);
                if (oldDepositIdOfDepositPerubahanListDepositPerubahan != null) {
                    oldDepositIdOfDepositPerubahanListDepositPerubahan.getDepositPerubahanList().remove(depositPerubahanListDepositPerubahan);
                    oldDepositIdOfDepositPerubahanListDepositPerubahan = em.merge(oldDepositIdOfDepositPerubahanListDepositPerubahan);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deposit deposit) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deposit persistentDeposit = em.find(Deposit.class, deposit.getDepositId());
            User userIdOld = persistentDeposit.getUserId();
            User userIdNew = deposit.getUserId();
            List<DepositPerubahan> depositPerubahanListOld = persistentDeposit.getDepositPerubahanList();
            List<DepositPerubahan> depositPerubahanListNew = deposit.getDepositPerubahanList();
            List<String> illegalOrphanMessages = null;
            for (DepositPerubahan depositPerubahanListOldDepositPerubahan : depositPerubahanListOld) {
                if (!depositPerubahanListNew.contains(depositPerubahanListOldDepositPerubahan)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DepositPerubahan " + depositPerubahanListOldDepositPerubahan + " since its depositId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                deposit.setUserId(userIdNew);
            }
            List<DepositPerubahan> attachedDepositPerubahanListNew = new ArrayList<DepositPerubahan>();
            for (DepositPerubahan depositPerubahanListNewDepositPerubahanToAttach : depositPerubahanListNew) {
                depositPerubahanListNewDepositPerubahanToAttach = em.getReference(depositPerubahanListNewDepositPerubahanToAttach.getClass(), depositPerubahanListNewDepositPerubahanToAttach.getDepositPerubahanId());
                attachedDepositPerubahanListNew.add(depositPerubahanListNewDepositPerubahanToAttach);
            }
            depositPerubahanListNew = attachedDepositPerubahanListNew;
            deposit.setDepositPerubahanList(depositPerubahanListNew);
            deposit = em.merge(deposit);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getDepositList().remove(deposit);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getDepositList().add(deposit);
                userIdNew = em.merge(userIdNew);
            }
            for (DepositPerubahan depositPerubahanListNewDepositPerubahan : depositPerubahanListNew) {
                if (!depositPerubahanListOld.contains(depositPerubahanListNewDepositPerubahan)) {
                    Deposit oldDepositIdOfDepositPerubahanListNewDepositPerubahan = depositPerubahanListNewDepositPerubahan.getDepositId();
                    depositPerubahanListNewDepositPerubahan.setDepositId(deposit);
                    depositPerubahanListNewDepositPerubahan = em.merge(depositPerubahanListNewDepositPerubahan);
                    if (oldDepositIdOfDepositPerubahanListNewDepositPerubahan != null && !oldDepositIdOfDepositPerubahanListNewDepositPerubahan.equals(deposit)) {
                        oldDepositIdOfDepositPerubahanListNewDepositPerubahan.getDepositPerubahanList().remove(depositPerubahanListNewDepositPerubahan);
                        oldDepositIdOfDepositPerubahanListNewDepositPerubahan = em.merge(oldDepositIdOfDepositPerubahanListNewDepositPerubahan);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deposit.getDepositId();
                if (findDeposit(id) == null) {
                    throw new NonexistentEntityException("The deposit with id " + id + " no longer exists.");
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
            Deposit deposit;
            try {
                deposit = em.getReference(Deposit.class, id);
                deposit.getDepositId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deposit with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DepositPerubahan> depositPerubahanListOrphanCheck = deposit.getDepositPerubahanList();
            for (DepositPerubahan depositPerubahanListOrphanCheckDepositPerubahan : depositPerubahanListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deposit (" + deposit + ") cannot be destroyed since the DepositPerubahan " + depositPerubahanListOrphanCheckDepositPerubahan + " in its depositPerubahanList field has a non-nullable depositId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = deposit.getUserId();
            if (userId != null) {
                userId.getDepositList().remove(deposit);
                userId = em.merge(userId);
            }
            em.remove(deposit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deposit> findDepositEntities() {
        return findDepositEntities(true, -1, -1);
    }

    public List<Deposit> findDepositEntities(int maxResults, int firstResult) {
        return findDepositEntities(false, maxResults, firstResult);
    }

    private List<Deposit> findDepositEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deposit.class));
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

    public Deposit findDeposit(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deposit.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepositCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deposit> rt = cq.from(Deposit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

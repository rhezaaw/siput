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
import model.Session;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.PengeluaranUser;
import model.IuranUser;
import model.Deposit;
import model.User;

/**
 *
 * @author fachrul
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getSessionList() == null) {
            user.setSessionList(new ArrayList<Session>());
        }
        if (user.getPengeluaranUserList() == null) {
            user.setPengeluaranUserList(new ArrayList<PengeluaranUser>());
        }
        if (user.getIuranUserList() == null) {
            user.setIuranUserList(new ArrayList<IuranUser>());
        }
        if (user.getDepositList() == null) {
            user.setDepositList(new ArrayList<Deposit>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Session> attachedSessionList = new ArrayList<Session>();
            for (Session sessionListSessionToAttach : user.getSessionList()) {
                sessionListSessionToAttach = em.getReference(sessionListSessionToAttach.getClass(), sessionListSessionToAttach.getSessionId());
                attachedSessionList.add(sessionListSessionToAttach);
            }
            user.setSessionList(attachedSessionList);
            List<PengeluaranUser> attachedPengeluaranUserList = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListPengeluaranUserToAttach : user.getPengeluaranUserList()) {
                pengeluaranUserListPengeluaranUserToAttach = em.getReference(pengeluaranUserListPengeluaranUserToAttach.getClass(), pengeluaranUserListPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserList.add(pengeluaranUserListPengeluaranUserToAttach);
            }
            user.setPengeluaranUserList(attachedPengeluaranUserList);
            List<IuranUser> attachedIuranUserList = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListIuranUserToAttach : user.getIuranUserList()) {
                iuranUserListIuranUserToAttach = em.getReference(iuranUserListIuranUserToAttach.getClass(), iuranUserListIuranUserToAttach.getIuranUserId());
                attachedIuranUserList.add(iuranUserListIuranUserToAttach);
            }
            user.setIuranUserList(attachedIuranUserList);
            List<Deposit> attachedDepositList = new ArrayList<Deposit>();
            for (Deposit depositListDepositToAttach : user.getDepositList()) {
                depositListDepositToAttach = em.getReference(depositListDepositToAttach.getClass(), depositListDepositToAttach.getDepositId());
                attachedDepositList.add(depositListDepositToAttach);
            }
            user.setDepositList(attachedDepositList);
            em.persist(user);
            for (Session sessionListSession : user.getSessionList()) {
                User oldUserIdOfSessionListSession = sessionListSession.getUserId();
                sessionListSession.setUserId(user);
                sessionListSession = em.merge(sessionListSession);
                if (oldUserIdOfSessionListSession != null) {
                    oldUserIdOfSessionListSession.getSessionList().remove(sessionListSession);
                    oldUserIdOfSessionListSession = em.merge(oldUserIdOfSessionListSession);
                }
            }
            for (PengeluaranUser pengeluaranUserListPengeluaranUser : user.getPengeluaranUserList()) {
                User oldUserIdOfPengeluaranUserListPengeluaranUser = pengeluaranUserListPengeluaranUser.getUserId();
                pengeluaranUserListPengeluaranUser.setUserId(user);
                pengeluaranUserListPengeluaranUser = em.merge(pengeluaranUserListPengeluaranUser);
                if (oldUserIdOfPengeluaranUserListPengeluaranUser != null) {
                    oldUserIdOfPengeluaranUserListPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListPengeluaranUser);
                    oldUserIdOfPengeluaranUserListPengeluaranUser = em.merge(oldUserIdOfPengeluaranUserListPengeluaranUser);
                }
            }
            for (IuranUser iuranUserListIuranUser : user.getIuranUserList()) {
                User oldUserIdOfIuranUserListIuranUser = iuranUserListIuranUser.getUserId();
                iuranUserListIuranUser.setUserId(user);
                iuranUserListIuranUser = em.merge(iuranUserListIuranUser);
                if (oldUserIdOfIuranUserListIuranUser != null) {
                    oldUserIdOfIuranUserListIuranUser.getIuranUserList().remove(iuranUserListIuranUser);
                    oldUserIdOfIuranUserListIuranUser = em.merge(oldUserIdOfIuranUserListIuranUser);
                }
            }
            for (Deposit depositListDeposit : user.getDepositList()) {
                User oldUserIdOfDepositListDeposit = depositListDeposit.getUserId();
                depositListDeposit.setUserId(user);
                depositListDeposit = em.merge(depositListDeposit);
                if (oldUserIdOfDepositListDeposit != null) {
                    oldUserIdOfDepositListDeposit.getDepositList().remove(depositListDeposit);
                    oldUserIdOfDepositListDeposit = em.merge(oldUserIdOfDepositListDeposit);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            List<Session> sessionListOld = persistentUser.getSessionList();
            List<Session> sessionListNew = user.getSessionList();
            List<PengeluaranUser> pengeluaranUserListOld = persistentUser.getPengeluaranUserList();
            List<PengeluaranUser> pengeluaranUserListNew = user.getPengeluaranUserList();
            List<IuranUser> iuranUserListOld = persistentUser.getIuranUserList();
            List<IuranUser> iuranUserListNew = user.getIuranUserList();
            List<Deposit> depositListOld = persistentUser.getDepositList();
            List<Deposit> depositListNew = user.getDepositList();
            List<String> illegalOrphanMessages = null;
            for (Session sessionListOldSession : sessionListOld) {
                if (!sessionListNew.contains(sessionListOldSession)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Session " + sessionListOldSession + " since its userId field is not nullable.");
                }
            }
            for (PengeluaranUser pengeluaranUserListOldPengeluaranUser : pengeluaranUserListOld) {
                if (!pengeluaranUserListNew.contains(pengeluaranUserListOldPengeluaranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PengeluaranUser " + pengeluaranUserListOldPengeluaranUser + " since its userId field is not nullable.");
                }
            }
            for (IuranUser iuranUserListOldIuranUser : iuranUserListOld) {
                if (!iuranUserListNew.contains(iuranUserListOldIuranUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IuranUser " + iuranUserListOldIuranUser + " since its userId field is not nullable.");
                }
            }
            for (Deposit depositListOldDeposit : depositListOld) {
                if (!depositListNew.contains(depositListOldDeposit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deposit " + depositListOldDeposit + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Session> attachedSessionListNew = new ArrayList<Session>();
            for (Session sessionListNewSessionToAttach : sessionListNew) {
                sessionListNewSessionToAttach = em.getReference(sessionListNewSessionToAttach.getClass(), sessionListNewSessionToAttach.getSessionId());
                attachedSessionListNew.add(sessionListNewSessionToAttach);
            }
            sessionListNew = attachedSessionListNew;
            user.setSessionList(sessionListNew);
            List<PengeluaranUser> attachedPengeluaranUserListNew = new ArrayList<PengeluaranUser>();
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUserToAttach : pengeluaranUserListNew) {
                pengeluaranUserListNewPengeluaranUserToAttach = em.getReference(pengeluaranUserListNewPengeluaranUserToAttach.getClass(), pengeluaranUserListNewPengeluaranUserToAttach.getPengeluaranUserId());
                attachedPengeluaranUserListNew.add(pengeluaranUserListNewPengeluaranUserToAttach);
            }
            pengeluaranUserListNew = attachedPengeluaranUserListNew;
            user.setPengeluaranUserList(pengeluaranUserListNew);
            List<IuranUser> attachedIuranUserListNew = new ArrayList<IuranUser>();
            for (IuranUser iuranUserListNewIuranUserToAttach : iuranUserListNew) {
                iuranUserListNewIuranUserToAttach = em.getReference(iuranUserListNewIuranUserToAttach.getClass(), iuranUserListNewIuranUserToAttach.getIuranUserId());
                attachedIuranUserListNew.add(iuranUserListNewIuranUserToAttach);
            }
            iuranUserListNew = attachedIuranUserListNew;
            user.setIuranUserList(iuranUserListNew);
            List<Deposit> attachedDepositListNew = new ArrayList<Deposit>();
            for (Deposit depositListNewDepositToAttach : depositListNew) {
                depositListNewDepositToAttach = em.getReference(depositListNewDepositToAttach.getClass(), depositListNewDepositToAttach.getDepositId());
                attachedDepositListNew.add(depositListNewDepositToAttach);
            }
            depositListNew = attachedDepositListNew;
            user.setDepositList(depositListNew);
            user = em.merge(user);
            for (Session sessionListNewSession : sessionListNew) {
                if (!sessionListOld.contains(sessionListNewSession)) {
                    User oldUserIdOfSessionListNewSession = sessionListNewSession.getUserId();
                    sessionListNewSession.setUserId(user);
                    sessionListNewSession = em.merge(sessionListNewSession);
                    if (oldUserIdOfSessionListNewSession != null && !oldUserIdOfSessionListNewSession.equals(user)) {
                        oldUserIdOfSessionListNewSession.getSessionList().remove(sessionListNewSession);
                        oldUserIdOfSessionListNewSession = em.merge(oldUserIdOfSessionListNewSession);
                    }
                }
            }
            for (PengeluaranUser pengeluaranUserListNewPengeluaranUser : pengeluaranUserListNew) {
                if (!pengeluaranUserListOld.contains(pengeluaranUserListNewPengeluaranUser)) {
                    User oldUserIdOfPengeluaranUserListNewPengeluaranUser = pengeluaranUserListNewPengeluaranUser.getUserId();
                    pengeluaranUserListNewPengeluaranUser.setUserId(user);
                    pengeluaranUserListNewPengeluaranUser = em.merge(pengeluaranUserListNewPengeluaranUser);
                    if (oldUserIdOfPengeluaranUserListNewPengeluaranUser != null && !oldUserIdOfPengeluaranUserListNewPengeluaranUser.equals(user)) {
                        oldUserIdOfPengeluaranUserListNewPengeluaranUser.getPengeluaranUserList().remove(pengeluaranUserListNewPengeluaranUser);
                        oldUserIdOfPengeluaranUserListNewPengeluaranUser = em.merge(oldUserIdOfPengeluaranUserListNewPengeluaranUser);
                    }
                }
            }
            for (IuranUser iuranUserListNewIuranUser : iuranUserListNew) {
                if (!iuranUserListOld.contains(iuranUserListNewIuranUser)) {
                    User oldUserIdOfIuranUserListNewIuranUser = iuranUserListNewIuranUser.getUserId();
                    iuranUserListNewIuranUser.setUserId(user);
                    iuranUserListNewIuranUser = em.merge(iuranUserListNewIuranUser);
                    if (oldUserIdOfIuranUserListNewIuranUser != null && !oldUserIdOfIuranUserListNewIuranUser.equals(user)) {
                        oldUserIdOfIuranUserListNewIuranUser.getIuranUserList().remove(iuranUserListNewIuranUser);
                        oldUserIdOfIuranUserListNewIuranUser = em.merge(oldUserIdOfIuranUserListNewIuranUser);
                    }
                }
            }
            for (Deposit depositListNewDeposit : depositListNew) {
                if (!depositListOld.contains(depositListNewDeposit)) {
                    User oldUserIdOfDepositListNewDeposit = depositListNewDeposit.getUserId();
                    depositListNewDeposit.setUserId(user);
                    depositListNewDeposit = em.merge(depositListNewDeposit);
                    if (oldUserIdOfDepositListNewDeposit != null && !oldUserIdOfDepositListNewDeposit.equals(user)) {
                        oldUserIdOfDepositListNewDeposit.getDepositList().remove(depositListNewDeposit);
                        oldUserIdOfDepositListNewDeposit = em.merge(oldUserIdOfDepositListNewDeposit);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Session> sessionListOrphanCheck = user.getSessionList();
            for (Session sessionListOrphanCheckSession : sessionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Session " + sessionListOrphanCheckSession + " in its sessionList field has a non-nullable userId field.");
            }
            List<PengeluaranUser> pengeluaranUserListOrphanCheck = user.getPengeluaranUserList();
            for (PengeluaranUser pengeluaranUserListOrphanCheckPengeluaranUser : pengeluaranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the PengeluaranUser " + pengeluaranUserListOrphanCheckPengeluaranUser + " in its pengeluaranUserList field has a non-nullable userId field.");
            }
            List<IuranUser> iuranUserListOrphanCheck = user.getIuranUserList();
            for (IuranUser iuranUserListOrphanCheckIuranUser : iuranUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the IuranUser " + iuranUserListOrphanCheckIuranUser + " in its iuranUserList field has a non-nullable userId field.");
            }
            List<Deposit> depositListOrphanCheck = user.getDepositList();
            for (Deposit depositListOrphanCheckDeposit : depositListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Deposit " + depositListOrphanCheckDeposit + " in its depositList field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

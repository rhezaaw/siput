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
import model.Session;
import model.User;

/**
 *
 * @author fachrul
 */
public class SessionJpaController implements Serializable {

    public SessionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Session session) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = session.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                session.setUserId(userId);
            }
            em.persist(session);
            if (userId != null) {
                userId.getSessionList().add(session);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Session session) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Session persistentSession = em.find(Session.class, session.getSessionId());
            User userIdOld = persistentSession.getUserId();
            User userIdNew = session.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                session.setUserId(userIdNew);
            }
            session = em.merge(session);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getSessionList().remove(session);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getSessionList().add(session);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = session.getSessionId();
                if (findSession(id) == null) {
                    throw new NonexistentEntityException("The session with id " + id + " no longer exists.");
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
            Session session;
            try {
                session = em.getReference(Session.class, id);
                session.getSessionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The session with id " + id + " no longer exists.", enfe);
            }
            User userId = session.getUserId();
            if (userId != null) {
                userId.getSessionList().remove(session);
                userId = em.merge(userId);
            }
            em.remove(session);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Session> findSessionEntities() {
        return findSessionEntities(true, -1, -1);
    }

    public List<Session> findSessionEntities(int maxResults, int firstResult) {
        return findSessionEntities(false, maxResults, firstResult);
    }

    private List<Session> findSessionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Session.class));
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

    public Session findSession(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Session.class, id);
        } finally {
            em.close();
        }
    }

    public int getSessionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Session> rt = cq.from(Session.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

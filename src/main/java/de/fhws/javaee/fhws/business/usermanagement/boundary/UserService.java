package de.fhws.javaee.fhws.business.usermanagement.boundary;

import de.fhws.javaee.fhws.business.usermanagement.controller.PWService;
import de.fhws.javaee.fhws.business.usermanagement.entity.FHWSLoginEvent;
import de.fhws.javaee.fhws.business.usermanagement.entity.FHWSUser;
import de.fhws.javaee.fhws.business.usermanagement.entity.LoginStatistic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class UserService {

    @Inject
    PWService pwService;

    @Inject
    EntityManager em;
    
    @Inject
    Event<FHWSLoginEvent> loginEvent;

    public List<FHWSUser> getAllFHWSUsers() {
        return em.createNamedQuery(FHWSUser.FIND_ALL, FHWSUser.class).getResultList();
    }

    public FHWSUser findUserById(Long id) {
        return em.find(FHWSUser.class, id);
    }

    public FHWSUser updateUser(FHWSUser user) {
        if (user.getId() == null)
            em.persist(user);
        else
            user = em.merge(user);
        return user;
    }

    public FHWSUser loginUser(String email, String password, String ipAddress) {
        List<FHWSUser> users = em.createNamedQuery(FHWSUser.FIND_BY_EMAIL, FHWSUser.class)
                .setParameter(FHWSUser.PARAM_EMAIL, email)
                .getResultList();

        if (users.isEmpty())
            return null;
        FHWSUser user = users.get(0);

        if (!pwService.checkPW(password, user.getPassword()))
            return null;

        LoginStatistic ls = new LoginStatistic();
        ls.setFhwsUser(user);
        ls.setIpAddress(ipAddress);

        if (user.getLoginStatistics() == null)
            user.setLoginStatistics(new ArrayList<LoginStatistic>());
        user.getLoginStatistics().add(ls);

        user.setLastLogin(new Date());
        
        System.out.println("vor fire event");
        loginEvent.fire(new FHWSLoginEvent(user));
        System.out.println("nach fire event");
        return user;

    }

}

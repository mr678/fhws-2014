/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhws.javaee.fhws.showcase.cdi;

import javax.inject.Inject;

/**
 *
 * @author Matthias Reining
 */
public class BusinessLogic {

    @Inject
    UserStatus userStatus;

    public void run() {

        userStatus.setStatus("war in CDITestServlet");
        System.out.println("in BusinessLogic#run");
    }

}

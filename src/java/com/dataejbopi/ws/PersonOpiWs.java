/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.ws;

import com.dataejbopi.entity.PersonOpi;
import com.dataejbopi.facade.PersonFacade;
import com.dataejbopi.vo.ROb;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Camilo
 */
@WebService(serviceName = "PersonOpiWs")
@Stateless()
public class PersonOpiWs {
    @EJB
    private PersonFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "findAll")
    public List<PersonOpi> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<PersonOpi> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "registerPerson")
    public ROb<PersonOpi> registerPerson(@WebParam(name = "cedule") Long cedule, @WebParam(name = "salary") Double salary, @WebParam(name = "epsId") Long epsId) {
        return ejbRef.registerPerson(cedule, salary, epsId);
    }

    @WebMethod(operationName = "validateLogin")
    public ROb<String> validateLogin(@WebParam(name = "userName") String userName, @WebParam(name = "userPassword") String userPassword) {
        return ejbRef.validateLogin(userName, userPassword);
    }

    @WebMethod(operationName = "findByCedule")
    public ROb<PersonOpi> findByCedule(@WebParam(name = "cedule") Long cedule) {
        return ejbRef.findByCedule(cedule);
    }

    @WebMethod(operationName = "removeByCedule")
    public ROb<PersonOpi> removeByCedule(@WebParam(name = "cedule") Long cedule) {
        return ejbRef.removeByCedule(cedule);
    }
    
}

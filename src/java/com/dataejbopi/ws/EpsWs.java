/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.ws;

import com.dataejbopi.entity.Eps;
import com.dataejbopi.facade.EpsFacade;
import com.dataejbsra.vo.ROb;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Usuario1
 */
@WebService(serviceName = "EpsWs")
@Stateless()
public class EpsWs {
    @EJB
    private EpsFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "findAll")
    public List<Eps> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Eps> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "registerEps")
    public ROb registerEps(@WebParam(name = "name") String name, @WebParam(name = "accountnumber") Long accountnumber) {
        return ejbRef.registerEps(name, accountnumber);
    }

    @WebMethod(operationName = "findById")
    public ROb findById(@WebParam(name = "id") Long id) {
        return ejbRef.findById(id);
    }

    @WebMethod(operationName = "removeById")
    public ROb removeById(@WebParam(name = "id") Long id) {
        return ejbRef.removeById(id);
    }
    
}

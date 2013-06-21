/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.ws;

import com.dataejbopi.entity.Pin;
import com.dataejbopi.facade.PinFacade;
import com.dataejbopi.vo.ROb;
import com.dataejbopi.vo.TransactionVo;
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
@WebService(serviceName = "PinWs")
@Stateless()
public class PinWs {
    @EJB
    private PinFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "findAll")
    public List<Pin> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Pin> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "registerPin")
    public ROb<Pin> registerPin(@WebParam(name = "personCedule") Long personCedule) {
        return ejbRef.registerPin(personCedule);
    }

    @WebMethod(operationName = "findById")
    public ROb<Pin> findById(@WebParam(name = "id") Long id) {
        return ejbRef.findById(id);
    }

    @WebMethod(operationName = "findByPerson")
    public ROb<List<Pin>> findByPerson(@WebParam(name = "cedule") Long cedule) {
        return ejbRef.findByPerson(cedule);
    }

    @WebMethod(operationName = "getLastPinCreated")
    public ROb<Pin> getLastPinCreated(@WebParam(name = "cedule") Long cedule) {
        return ejbRef.getLastPinCreated(cedule);
    }

    @WebMethod(operationName = "updatePin")
    public ROb<Pin> updatePin(@WebParam(name = "pin") Pin pin) {
        return ejbRef.updatePin(pin);
    }

    @WebMethod(operationName = "removeById")
    public ROb<Pin> removeById(@WebParam(name = "id") Long id) {
        return ejbRef.removeById(id);
    }

    @WebMethod(operationName = "getTransactionData")
    public ROb<List<TransactionVo>> getTransactionData(@WebParam(name = "id") Long id) {
        return ejbRef.getTransactionData(id);
    }
    
}

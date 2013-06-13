/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.ws;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.facade.PaymentFacade;
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
@WebService(serviceName = "PaymentWs")
@Stateless()
public class PaymentWs {
    @EJB
    private PaymentFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "findAll")
    public List<Payment> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Payment> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "createPaymentForPin")
    public ROb createPaymentForPin(@WebParam(name = "idPin") Long idPin, @WebParam(name = "salary") Double salary) {
        return ejbRef.createPaymentForPin(idPin, salary);
    }

    @WebMethod(operationName = "registerPaymentComplete")
    public ROb registerPaymentComplete(@WebParam(name = "idPayment") Long idPayment, @WebParam(name = "idTransaction") Long idTransaction) {
        return ejbRef.registerPaymentComplete(idPayment, idTransaction);
    }

    @WebMethod(operationName = "updatePaymentExtemporaneous")
    public ROb updatePaymentExtemporaneous(@WebParam(name = "idPayment") Long idPayment) {
        return ejbRef.updatePaymentExtemporaneous(idPayment);
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

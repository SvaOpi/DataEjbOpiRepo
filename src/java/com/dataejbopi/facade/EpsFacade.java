/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Eps;
import com.dataejbsra.vo.ROb;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario1
 */
@Stateless
public class EpsFacade extends AbstractFacade<Eps> {
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EpsFacade() {
        super(Eps.class);
    }
    
    public ROb registerEps(String name, Long accountnumber){
        ROb rob = new ROb();
        try{
            Eps eps = new Eps();
            eps.setName(name);
            eps.setAccountnumber(accountnumber);
            create(eps);
            List<Eps> listEps = findAll();
            eps = listEps.get(listEps.size()-1);
            rob.setData(eps);
            rob.setSuccess(true);
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed Transaction!");
            return rob;
        }
    }
    
    public ROb findById(Long id){
        ROb rob = new ROb();
        try{
            Eps eps = find(id);
            if(eps==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                rob.setData(eps);
                rob.setSuccess(true);
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb removeById(Long id){
        ROb rob = new ROb();
        try{
            rob = findById(id);
            if(rob.isSuccess()==true){
                Eps eps = (Eps) rob.getData();
                remove(eps);
                rob.setSuccess(true);
                rob.setData(null);
            }            
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }    
}

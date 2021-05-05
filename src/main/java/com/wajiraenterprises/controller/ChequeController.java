package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ChequeDao;
import com.wajiraenterprises.dao.ChequestatusDao;
import com.wajiraenterprises.dao.CompanyDao;
import com.wajiraenterprises.dao.CreditDao;
import com.wajiraenterprises.entity.Cheque;
import com.wajiraenterprises.entity.Credit;
import com.wajiraenterprises.entity.Payment;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ChequeController {

    @Autowired
    private ChequeDao chequeDao;

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ChequestatusDao chequestatusDao;

    @RequestMapping(value = "/cheque", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Cheque> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.CHEQUE,AuthProvider.SELECT)) {
            return chequeDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Update**/
    @RequestMapping(value = "/cheque", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Cheque cheque) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CHEQUE,AuthProvider.UPDATE)) {
            Cheque chequeWithPayment = chequeDao.findByChequeNo(cheque.getChequenumber());
            Credit credit = creditDao.findByCompanyNumber(chequeWithPayment.getPaymentId().getCompanyId().getNumber());
                try {
                    if (cheque.getChequestatusId().getId()==3){
                        credit.setValue(credit.getValue().add(cheque.getValue()));
                    }
                    cheque.setPaymentId(chequeWithPayment.getPaymentId());
                    chequeDao.save(cheque);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }


        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/cheque", params = {"page", "size","chequenumber","clientid","statusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Cheque> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("chequenumber") String chequenumber, @RequestParam("clientid") Integer clientid, @RequestParam("statusid") Integer statusid) {

         System.out.println(clientid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CHEQUE,AuthProvider.SELECT)) {

            List<Cheque> cheques = chequeDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Cheque> chequeStream = cheques.stream();


            if (clientid != null)
                chequeStream = chequeStream.filter(c -> c.getPaymentId().getCompanyId().equals(companyDao.getOne(clientid)));
            if (statusid != null)
                chequeStream = chequeStream.filter(c -> c.getChequestatusId().equals(chequestatusDao.getOne(statusid)));
            chequeStream = chequeStream.filter(c -> c.getChequenumber().contains(chequenumber));

            List<Cheque> cheques2 = chequeStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < cheques2.size() ? start + size : cheques2.size();
            Page<Cheque> emppage = new PageImpl<>(cheques2.subList(start, end), PageRequest.of(page, size), cheques2.size());

            return emppage;
        }

        return null;

    }
    

}

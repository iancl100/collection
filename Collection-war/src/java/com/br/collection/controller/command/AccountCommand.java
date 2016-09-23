/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.collection.controller.command;

import com.br.collection.model.dao.AccountDAO;
import com.br.collection.model.entities.Account;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author First Place
 */
public class AccountCommand implements  Command{

    AccountDAO accountDAO = lookupAccountDAOBean();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String responsePage;
    
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        String action = request.getParameter("command").split("\\.")[1];
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account accountTemp = null;
        switch(action){
            case "login":
                    accountTemp=accountDAO.readByUsername(username);
                    if(accountTemp!=null){
                        if(password.equals(accountTemp.getPassword())){
                            request.getSession().setAttribute("account", accountTemp);
                            
                            responsePage="painel.jsp";
                        }else{
                            request.getSession().setAttribute("error", "Senhas não correspondem");
                            responsePage="index.jsp";
                        }
                    }else{
                        request.getSession().setAttribute("error", "Usuário não existe");
                        responsePage="index.jsp";
                    }
                break;
                case "logout":
                request.getSession().invalidate();
                responsePage=request.getContextPath();
                break;
        }
    }

    @Override
    public String getResponsePage() {
        return this.responsePage;
    }

    private AccountDAO lookupAccountDAOBean() {
        try {
            Context c = new InitialContext();
            return (AccountDAO) c.lookup("java:global/Collection/Collection-ejb/AccountDAO!com.br.collection.model.dao.AccountDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}

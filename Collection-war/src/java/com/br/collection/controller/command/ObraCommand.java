/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.collection.controller.command;


import com.br.collection.model.dao.AccountDAO;
import com.br.collection.model.dao.ObraDAO;
import com.br.collection.model.entities.Account;
import com.br.collection.model.entities.Obra;
import java.util.List;
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
public class ObraCommand implements  Command{

    AccountDAO accountDAO = lookupAccountDAOBean();
    ObraDAO obraDAO = lookupObraDAOBean();

    
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
        List<Obra> lista=null;
        switch(action){
            case "buscar":
                lista=obraDAO.read();
                request.getSession().setAttribute("lista", lista);
                responsePage="painel.jsp";
                break;
            case "solicitar":
                long idObra= Long.parseLong(request.getParameter("idObra"));
                long idAccount= Long.parseLong(request.getParameter("idAccount"));
                Obra obra = obraDAO.readById(idObra);
                Account account=accountDAO.readById(idAccount);
                obra.setAccount(account);
                obraDAO.update(obra);
                account=accountDAO.readById(idAccount);
                request.getSession().invalidate();
                request.getSession().setAttribute("account", account);
                request.getSession().removeAttribute("lista");
                responsePage="painel.jsp";
                break;
            case "remover":
                idObra= Long.parseLong(request.getParameter("idObra"));
                idAccount= Long.parseLong(request.getParameter("idAccount"));
                obra = obraDAO.readById(idObra);
                obra.setAccount(null);
                obraDAO.update(obra);
                account=accountDAO.readById(idAccount);
                request.getSession().invalidate();
                request.getSession().setAttribute("account", account);
                request.getSession().removeAttribute("lista");
                responsePage="painel.jsp";
                break;
        }
    }

    @Override
    public String getResponsePage() {
        return this.responsePage;
    }

    private ObraDAO lookupObraDAOBean() {
        try {
            Context c = new InitialContext();
            return (ObraDAO) c.lookup("java:global/Collection/Collection-ejb/ObraDAO!com.br.collection.model.dao.ObraDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

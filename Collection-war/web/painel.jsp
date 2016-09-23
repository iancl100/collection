<%-- 
    Document   : painel
    Created on : 22/09/2016, 14:40:28
    Author     : First Place
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/painel.css" rel="stylesheet" type="text/css"/>
        <title>Painel de Obras</title>
        <script>
            function load() {
                document.getElementById("ObrasForm").submit();
            }
        </script>
    </head>
    <body onload="load()">
        <c:if test="${account==null}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:if>
        <c:if test="${lista==null}" >
            <form action="Controller" method="POST" id="ObrasForm">
                <input type="hidden" name="command" value="Obra.buscar">
            </form>
        </c:if>
        <nav>
            <ul>
                <li><img src="img/ic_account_box_black_36dp_1x.png"></li>
                <li><p>${account.username}</p></li>
                <li>
                    <form action="Controller" method="POST">
                        <input type="hidden" name="command" value="Account.logout">
                        <button class="btnLogout">LOGOUT</button>
                    </form>
                </li>
            </ul>
        </nav>
        <section>
            <h1>Obras</h1>
            <table>
                <tr>
                    <th>Obra</th>
                    <th>Status</th>
                    <th>Solicitar</th>
                    <th>Descrição</th>
                </tr>
                <c:forEach items="${lista}" var="Obra">
                    <tr>
                        <td>${Obra.nome}</td>

                        <td>
                            <c:if test="${Obra.account.idAccount!=null}">
                                Indisponível<c:if test="${account.adm==true}">: ${Obra.account.username}</c:if>
                                <c:set var="veri" value="${false}"></c:set>
                            </c:if>
                            <c:if test="${Obra.account.idAccount==null}">
                                Disponível
                                <c:set var="veri" value="${true}"></c:set>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${veri}">
                                <form action="Controller" method="POST">
                                    <input type="hidden" name="command" value="Obra.solicitar">
                                    <input type="hidden" name="idObra" value="${Obra.idObra}">
                                    <input type="hidden" name="idAccount" value="${account.idAccount}">
                                    <button type="submit" class="btn">Solicitar</button>
                                </form>
                            </c:if>
                            <c:if test="${Obra.account.idAccount==account.idAccount or (account.adm==true and Obra.account.idAccount!=null)}">
                                <form action="Controller" method="POST">
                                    <input type="hidden" name="command" value="Obra.remover">
                                    <input type="hidden" name="idObra" value="${Obra.idObra}">
                                    <input type="hidden" name="idAccount" value="${Obra.account.idAccount}">
                                    <button type="submit" class="btnX">x</button>
                                </form>
                            </c:if>
                        </td>
                        <td>${Obra.descricao}</td>
                    </tr>
                </c:forEach>
            </table>
            </section>
            <section>
                <h1>Suas solicitações</h1>
                <table>
                    <tr>
                        <th>Obra</th>
                        <th>Status</th>
                        <th>Solicitar</th>
                        <th>Descrição</th>
                    </tr>
                    <c:forEach items="${account.obraList}" var="obra">
                        <tr>
                            <td>${obra.nome}</td>
                            <td>Solicitado</td>
                            <td>
                                <form action="Controller" method="POST">
                                    <input type="hidden" name="command" value="Obra.remover">
                                    <input type="hidden" name="idObra" value="${obra.idObra}">
                                    <input type="hidden" name="idAccount" value="${obra.account.idAccount}">
                                    <button type="submit" class="btnX">x</button>
                                </form>
                            </td>
                            <td>${obra.descricao}</td>
                        </tr>
                    </c:forEach>
                </table>
            </section>
        </section>
    </body>
</html>

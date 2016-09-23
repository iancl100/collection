<%-- 
    Document   : index
    Created on : 22/09/2016, 09:27:25
    Author     : 31535811
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <title>Home</title>
    </head>
    <body>
        <section>
            <article>
                <h2>Login</h2>
            </article>
            <form action="Controller" method="POST">
                <input type="hidden" name="command" value="Account.login">
                <input type="text" name="username" placeholder="Usuário">
                <input type="password" name="password" placeholder="Senha">
                <button type="submit" class="btn">LOGIN</button>
            </form>
        </section>
    </body>
</html>

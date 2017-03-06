<%@ page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Select custom translation</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="http://www.htmlhelp.com/style.css" />
        <link rel="stylesheet" href="http://www.free-css.com/profile/styles/layout.css" />
    </head>
    <body>
    <h2>Here you can edit translation of an existing word</h2>
    <br />
    <p>Submitted : <%= request.getParameter("word") %></p>
    <hr />
    <p>Enter the new custom translation:</p>
    <form method="POST" action = "servlet">
        <input type="hidden" name="word" value=<%= request.getParameter("word") %> />
        <input type="text" name="newtranslation" />
        <input type="submit" value = "Submit" />
    </form>
    </body>
</html>
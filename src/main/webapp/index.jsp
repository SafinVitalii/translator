<%@ page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
    <head>
    <title>Translate App JSP</title>
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="http://www.htmlhelp.com/style.css" />
    <link rel="stylesheet" href="http://www.free-css.com/profile/styles/layout.css" />
    </head>
<body>
    <h2>TomCat Servlet Example</h2>
    <div id = "translator">
        <div id = "header" class = "fl_left">
        <h1>Translator</h1>
    </div>
    <div id = "main" class="imgl">
        <form action = "servlet" method = "post">
        <h3> Word </h3>
        <input type = "text" name = "basetext" value = "${basetext}" />
            <select name = "select_base">
            <option value = "${base_lang}">${base_lang}</option>
            <option value = "${translating_lang}">${translating_lang}</option>
            </select>
        <h3> Translation </h3>
        <input type = "text" name = "translation" value = "${translation}" />
            <select name = "select_translating">
            <option value = "${translating_lang}">${translating_lang}</option>
            <option value = "${base_lang}">${base_lang}</option>
            </select>
        <hr />
        <input type = "submit" value = "Translate" />
        </form>
     </div>
     </div>
</body>
</html>
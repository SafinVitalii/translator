<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8" />
    <title>Translate App JSP</title>
    <link rel="stylesheet" href="resources/css/style.css" />
    <style type="text/css">
    </style>
    </head>
<body>
    <h2>TomCat Servlet Example</h2>
    <div id = "translator">
        <div id = "header">
        <h1 align = "center">Translator</h1>
    </div>
    <div id = "main">
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
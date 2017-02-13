package com.safin.translator;
/**
 * Created by vitalii.safin on 2/7/2017.
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
/*  <servlet-mapping>
    <servlet-name>translator</servlet-name>
    <url-pattern>/servlet</url-pattern>
  </servlet-mapping> */

@WebServlet (
    name="translator",
        urlPatterns="/servlet/*"
)
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        if (user == null) {
            user = "Guest";
        }
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/css;charset=UTF-8");
        request.setAttribute("translation","-");
        request.setAttribute("basetext", "+");
        request.setAttribute("base_lang", "English");
        request.setAttribute("translating_lang", "Ukrainian");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("basetext");
        byte[] bytes = text.getBytes("ISO-8859-1");
        text = new String(bytes, "UTF-8");
        if (text == null || text.length() == 0) {
            return;
        }
        String user = request.getParameter("user");
        if (user == null) {
            user = "Guest";
        }

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String translation = null;
        Translator translator = new Translator();
        InternetConnectionQA tester = new InternetConnectionQA();
        tester.checkConnection();
        translator.setBaseLanguage(request.getParameter("select_base"));
        translator.setTranslatingLanguage(request.getParameter("select_translating"));
        try {
            translation = translator.beginTranslation(text);
        } catch (Exception e) {
            System.out.println("Exception during translation : " + e.toString());
        }
        System.out.println("Input : " + text + " Output : " + translation);
        request.setAttribute("translation",translation);
        request.setAttribute("basetext", text);
        request.setAttribute("base_lang", translator.getBaseLanguage());
        request.setAttribute("translating_lang", translator.getTranslatingLanguage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet started.");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet ended.");
    }
}

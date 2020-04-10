package com.csfrez.demo.servlet.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从HttpSession获取当前用户名:
        String user = (String) req.getSession().getAttribute("user");
        String lang = parseLanguageFromCookie(req);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("X-Powered-By", "JavaEE Servlet");
        PrintWriter pw = resp.getWriter();

        if (lang.equals("zh")) {
            pw.write("<h1>你好, " + (user != null ? user : "Guest") + "</h1>");
            if (user == null) {
                // 未登录，显示登录链接:
                pw.write("<p><a href=\"/session/signin\">登录</a></p>");
            } else {
                // 已登录，显示登出链接:
                pw.write("<p><a href=\"/session/signout\">登出</a></p>");
            }
        } else {
            pw.write("<h1>Welcome, " + (user != null ? user : "Guest") + "</h1>");
            if (user == null) {
                pw.write("<p><a href=\"/session/signin\">Sign In</a></p>");
            } else {
                pw.write("<p><a href=\"/session/signout\">Sign Out</a></p>");
            }
        }
        pw.write("<p><a href=\"/pref?lang=en\">English</a> | <a href=\"/pref?lang=zh\">中文</a>");

        pw.flush();
    }

    private String parseLanguageFromCookie(HttpServletRequest req) {
        // 获取请求附带的所有Cookie:
        Cookie[] cookies = req.getCookies();
        // 如果获取到Cookie:
        if (cookies != null) {
            // 循环每个Cookie:
            for (Cookie cookie : cookies) {
                // 如果Cookie名称为lang:
                if (cookie.getName().equals("lang")) {
                    // 返回Cookie的值:
                    return cookie.getValue();
                }
            }
        }
        // 返回默认值:
        return "en";
    }
}

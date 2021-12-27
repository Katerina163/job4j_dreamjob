package ru.job4j.dream.servlet;

import ru.job4j.dream.Config;
import ru.job4j.dream.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean res = MemStore.instOf().deleteByIdCandidate(id);
        Config config = new Config();
        boolean rsl = new File(config.value("photo") + id + ".jpg").delete();
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
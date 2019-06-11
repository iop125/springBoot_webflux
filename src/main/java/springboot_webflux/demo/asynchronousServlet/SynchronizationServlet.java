package springboot_webflux.demo.asynchronousServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns="/synServlet",name="synchronizationServlet")
public class SynchronizationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("begin");
        try {
            doSome(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

    public void doSome(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        TimeUnit.SECONDS.sleep(5);
        resp.getWriter().println("Done");
    }
}

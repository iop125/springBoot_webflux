package springboot_webflux.demo.sse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns = "/sseServlet", name = "SSEServlet")
public class SSEServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("---------");
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("utf-8");
        PrintWriter p = resp.getWriter();
        for (int i = 0; i < 10; i++) {
           p.print("event:test\n");
           p.print("data:"+i+"\n");
           p.print("\r\n");
           p.flush();
            try {
                doSome();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void doSome() throws Exception {
        TimeUnit.SECONDS.sleep(1);
    }
}

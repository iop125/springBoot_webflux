package springboot_webflux.demo.asynchronousServlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步servlet
 */
@WebServlet(value = "/asyncServlet", asyncSupported = true)
public class AsynchronousServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("begin");
        //获取异步上下文，开启异步操作
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(20*1000);
        //获取非阻塞的异步请求和响应
        ServletRequest servletRequest = asyncContext.getRequest();
        ServletResponse servletResponse =
                asyncContext.getResponse();

        CompletableFuture.runAsync(() -> doSome(asyncContext, servletRequest, servletResponse));
        System.out.println("end");
    }

    public void doSome(AsyncContext asyncContext, ServletRequest req, ServletResponse resp) {
        try {
            TimeUnit.SECONDS.sleep(5);

            resp.getWriter().println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //通知耗时业务操作 完成
        asyncContext.complete();
    }
}

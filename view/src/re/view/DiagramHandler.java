package re.view;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class DiagramHandler extends AbstractHandler{

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	
}

//{
//    final String greeting;
//    final String body;
//
//    public DiagramHandler()
//    {
//        this("Hello World");
//    }
//
//    public DiagramHandler( String greeting )
//    {
//        this(greeting, null);
//    }
//
//    public DiagramHandler( String greeting, String body )
//    {
//        this.greeting = greeting;
//        this.body = body;
//    }
//
//    public void handle( String target,
//                        Request baseRequest,
//                        HttpServletRequest request,
//                        HttpServletResponse response ) throws IOException,
//                                                      ServletException
//    {
//        response.setContentType("text/html; charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        PrintWriter out = response.getWriter();
//
//        out.println("<h1>" + greeting + "</h1>");
//        if (body != null)
//        {
//            out.println(body);
//        }
//
//        baseRequest.setHandled(true);
//    }
//}
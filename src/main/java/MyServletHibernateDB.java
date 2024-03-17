import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.Employee;
import util.UtilDB;

@WebServlet("/MyServletHibernateDB")
public class MyServletHibernateDB extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServletHibernateDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");

      // #1
      //UtilDB.createEmployees("user3", "33");
      //UtilDB.createEmployees("user4", "44");
      
      // #2
      retrieveDisplayData(response.getWriter());
   }

   void retrieveDisplayData(PrintWriter out) {
      String title = "To Dos";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      out.println("<ul>");
      List<Employee> listEmployees = UtilDB.listEmployees();
      for (Employee employee : listEmployees) {
         System.out.println("[DBG] " + employee.getId() + ", " //
               + employee.getName() + ", " //
               + employee.getAge());

         out.println("<li>" + employee.getId() + ", " //
               + employee.getName() + ", " //
               + employee.getAge() + "</li>");
      }
      out.println("</ul>");
      out.println("</body></html>");
      //Adding a reference link to insert.
      out.println("<a href=\'/webproject-orm/simpleInsertHB.html\'>Insert</a>");
      out.println("<a href=\'/webproject-orm/simpleSearchHB.html\'>Search</a>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}

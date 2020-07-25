package com.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crud.beans.Student;
import com.crud.factories.StudentServiceFactory;
import com.crud.service.StudentService;

@WebServlet("*.do")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String requestURI = request.getRequestURI();
//		System.out.println(requestURI);
		
		Student student = null;
		StudentService studentService = StudentServiceFactory.getStudentService();
		RequestDispatcher requestDispatcher = null;
		
		String sid = "";
		String sname = "";
		String saddr = "";
		String status = "";
		
//		requestURI ---> (add.do) ==>
		
		if (requestURI.endsWith("add.do")) {
			sid = request.getParameter("sid");
			sname = request.getParameter("sname");
			saddr = request.getParameter("saddr");
			
			student = new Student();
			student.setSid(sid);
			student.setSname(sname);
			student.setSaddr(saddr);
			
			status = studentService.addStudent(student);
			
			if (status.equals("exists")) {
				requestDispatcher = request.getRequestDispatcher("exists.html");
				requestDispatcher.forward(request, response);
			}

			if (status.equals("success")) {
				requestDispatcher = request.getRequestDispatcher("success.html");
				requestDispatcher.forward(request, response);
			}
			
			if (status.equals("failure")) {
				requestDispatcher = request.getRequestDispatcher("failure.html");
				requestDispatcher.forward(request, response);
			}
		}
		
//		requestURI ---> (search.do) ==>
		
		if (requestURI.endsWith("search.do")) {
			sid = request.getParameter("sid");
			student = studentService.searchStudent(sid);
			if (student == null) {
				requestDispatcher = request.getRequestDispatcher("nonexists.html");
				requestDispatcher.forward(request, response);
			} else {
				out.println("<html><body>");
				out.println("<br><br><br><br>");
				out.println("<h3 style='color: blue;' align='center'>Student Details</h3>");
				out.println("<table align='center' border='1'>");
				out.println("<tr><td>ID</td><td>" + student.getSid() + "</td></tr>");
				out.println("<tr><td>Name</td><td>" + student.getSname() + "</td></tr>");
				out.println("<tr><td>Address</td><td>" + student.getSaddr() + "</td></tr>");
				out.println("</table>");
				out.println("</body></html>");
			}
		}
		
//		requestURI --> (edit.do) ==>
		
		if(requestURI.endsWith("edit.do")) {
			
			sid = request.getParameter("sid");
			student = studentService.searchStudent(sid);
			
			if (student == null) {
				requestDispatcher = request.getRequestDispatcher("nonexists.html");
				requestDispatcher.forward(request, response);
			} else {
				out.println("<html><body>");
				out.println("<br><br><br><br>");
				out.println("<h3 style='color: blue;' align='center'>EDIT FORM</h3>");
				out.println("<form method='post' action='./update.do'>");
				out.println("<table align='center'>");
				out.println("<tr><td>ID: </td><td>" + student.getSid() + "</td></tr>");
				out.println("<input type='hidden' name='sid' value='" + student.getSid() +"'>");
				out.println("<tr><td>Name</td><td><input type='text' name='sname' value='" + student.getSname() + "'</td></tr>");
				out.println("<tr><td>Address</td><td><input type='text' name='saddr' value='" + student.getSaddr() + "'</td></tr>");
				out.println("<tr><td><input type='submit' value='UPDATE'></td></tr>");
				out.println("</table></form>");
				out.println("</body></html>");
				
			}
		}
		
//		requestURI --> (update.do) ==>
		
		if (requestURI.endsWith("update.do")) {
			sid = request.getParameter("sid");
			sname = request.getParameter("sname");
			saddr = request.getParameter("saddr");
			
			student = new Student();
			student.setSid(sid);
			student.setSname(sname);
			student.setSaddr(saddr);
			
			status = studentService.updateStudent(student);
			
			if (status == "success") {
				requestDispatcher = request.getRequestDispatcher("success.html");
				requestDispatcher.forward(request, response);
			}

			if (status == "failure") {
				requestDispatcher = request.getRequestDispatcher("failure.html");
				requestDispatcher.forward(request, response);
			}
			
		}
		
//		requestURI --> (delete.do) ==>
		
		if (requestURI.endsWith("delete.do")) {
			sid = request.getParameter("sid");
			
			status = studentService.deleteStudent(sid);
			
			if (status == "nonexists") {
				requestDispatcher = request.getRequestDispatcher("nonexists.html");
				requestDispatcher.forward(request, response);
			}

			if (status == "success") {
				requestDispatcher = request.getRequestDispatcher("success.html");
				requestDispatcher.forward(request, response);
			}
			
			if (status == "failure") {
				requestDispatcher = request.getRequestDispatcher("failure.html");
				requestDispatcher.forward(request, response);
			}
			
		}
		
	}

}













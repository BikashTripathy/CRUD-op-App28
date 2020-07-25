package com.crud.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.crud.beans.Student;
import com.crud.factories.ConnectionFactory;

public class StudentDaoImpl implements StudentDao {

	@Override
	public String add(Student student) {
		String status = "";
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement st = connection.createStatement();
			Student std = search(student.getSid());
			if (std == null) {
				int rowCount = st.executeUpdate("insert into students values('" + student.getSid() + "', '" + student.getSname() + "', '" + student.getSaddr() + "')");
				if (rowCount == 1) {
					status = "success";
				} else {
					status = "failure";
				}
			} else {
				status = "exists";
			}
			
		} catch (Exception e) {
			status = "failure";
			e.printStackTrace();
		}
		
		return status;
	}

	@Override
	public Student search(String sid) {
		Student student = null;
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from students WHERE SID = '" + sid +"'");
			boolean b = rs.next();
			
			if (b == true) {
				student = new Student();
				student.setSid(rs.getString("SID"));
				student.setSname(rs.getString("SNAME"));
				student.setSaddr(rs.getString("SADDR"));
			}else {
				student = null;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public String update(Student student) {
		String status = "";
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement st = connection.createStatement();
			int rowCount = st.executeUpdate("update students set SNAME='" + student.getSname() + "', SADDR='" + student.getSaddr() + "' WHERE SID='" + student.getSid() + "'");
			
			if (rowCount == 1) {
				status = "success";
			} else {
				status = "failure";
			}
			
		} catch (Exception e) {
			status = "failure";
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String delete(String sid) {
		String status = "";
		try {
			Student std = search(sid);
			
			if (std == null) {
				status = "nonexists";
			} else {
				Connection connection = ConnectionFactory.getConnection();
				Statement st = connection.createStatement();
				
				int rowCount = st.executeUpdate("delete from students WHERE sid ='" + sid + "'");
				
				if (rowCount == 1) {
					status = "success";
				}else {
					status = "failure";
				}
			}
			
		} catch (Exception e) {
			status = "failure";
			e.printStackTrace();
		}

		return status;
	}

}














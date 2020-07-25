package com.crud.dao;

import com.crud.beans.Student;

public interface StudentDao {
	public String add(Student student);
	public Student search(String sid);
	public String update(Student student);
	public String delete(String sid);
}

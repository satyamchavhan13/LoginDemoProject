package com.hibernate.crud.operations;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.Student;
import com.utils.HibernateUtil;

public class DbOperations {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static final Logger logger = LogManager.getLogger(DbOperations.class);

	// Method 1: This Method Used To Create A New Student Record In The Database
	// Table
	public static void createRecord() {
		int count = 0;
		Student studentObj = null;
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();

			for (int j = 101; j <= 105; j++) {
				count = count + 1;
				studentObj = new Student();
				studentObj.setRollNumber(j);
				studentObj.setStudentName("Darshit " + j);
				studentObj.setCourse("Python");
				sessionObj.save(studentObj);
			}
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Created '" + count + "' Records In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	// Method 2: This Method Is Used To Display The Records From The Database Table
	@SuppressWarnings("unchecked")
	public static List<Student> displayRecords() {
		List<Student> studentsList = new ArrayList<Student>();
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();
			studentsList = sessionObj.createQuery("FROM Student").list();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return studentsList;
	}

	// Method 3: This Method Is Used To Update A Record In The Database Table
	public static void updateRecord(int student_id) {
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();
			Student stuObj = (Student) sessionObj.get(Student.class, student_id);
			stuObj.setStudentName("Darshit");
			stuObj.setCourse("Java");
			sessionObj.getTransaction().commit();
			logger.info("\nStudent With Id?= " + student_id + " Is Successfully Updated In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	// Method 4(a): This Method Is Used To Delete A Particular Record From The
	// Database Table
	public static void deleteRecord(Integer student_id) {
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();

			Student studObj = findRecordById(student_id);
			sessionObj.delete(studObj);
			sessionObj.getTransaction().commit();
			logger.info("\nStudent With Id?= " + student_id + " Is Successfully Deleted From The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	// Method 4(b): This Method To Find Particular Record In The Database Table
	public static Student findRecordById(Integer find_student_id) {
		Student findStudentObj = null;
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();
			findStudentObj = (Student) sessionObj.load(Student.class, find_student_id);
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return findStudentObj;
	}

	// Method 5: This Method Is Used To Delete All Records From The Database Table
	public static void deleteAllRecords() {
		try {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
			sessionObj.beginTransaction();

			Query queryObj = sessionObj.createQuery("DELETE FROM Student");
			queryObj.executeUpdate();
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Deleted All Records From The Database Table!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}
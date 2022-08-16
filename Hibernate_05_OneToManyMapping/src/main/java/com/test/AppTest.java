package com.test;

import org.hibernate.Session;

import com.entity.AccountEntity;
import com.entity.EmployeeEntity;
import com.utils.HibernateUtil;
import java.util.HashSet;
import java.util.Set;

public class AppTest {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// for Employee 1 add data having many account with one employee ID
		
		AccountEntity account1 = new AccountEntity();
		account1.setAccountNumber("Account Satyam 0001");

		AccountEntity account2 = new AccountEntity();
		account2.setAccountNumber("Account Chavhan 0002");
		
		EmployeeEntity firstEmployee = new EmployeeEntity();
		firstEmployee.setEmail("satyamchavhan13@gmail.com");
		firstEmployee.setFirstName("satyam");
		firstEmployee.setLastName("chavhan");
		
		Set<AccountEntity> accountsOfFirstEmployee = new HashSet<AccountEntity>();
		accountsOfFirstEmployee.add(account1);
		accountsOfFirstEmployee.add(account2);
		
		firstEmployee.setAccounts(accountsOfFirstEmployee);

		// for employee 2 having 1 account 
		
		AccountEntity account3 = new AccountEntity();
		account3.setAccountNumber("Account Karan 00003");

		EmployeeEntity secondEmployee = new EmployeeEntity();
		secondEmployee.setEmail("Karan@gmail.com");
		secondEmployee.setFirstName("Karan");
		secondEmployee.setLastName("sharma");

		Set<AccountEntity> accountsOfSecondEmployee = new HashSet<AccountEntity>();
		accountsOfSecondEmployee.add(account3);
		
		secondEmployee.setAccounts(accountsOfSecondEmployee);
		
		// Save Employee
		session.save(firstEmployee);
		session.save(secondEmployee);

		session.getTransaction().commit();

		session.close();
	}
}
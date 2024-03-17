/**
 */
package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SQLQuery;
import datamodel.Employee;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @since JavaSE-1.8
 */
public class UtilDB {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<Employee> listEmployees() {
      List<Employee> resultList = new ArrayList<Employee>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         List<?> employees = session.createQuery("FROM Employee").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            resultList.add(employee);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<Employee> listEmployees(String keyword) {
      List<Employee> resultList = new ArrayList<Employee>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         List<?> employees = session.createQuery("FROM Employee").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            if (employee.getName().startsWith(keyword)) {
               resultList.add(employee);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void createEmployees(String userName, String age) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         //session.save(new Employee(userName, Integer.valueOf(age)));
         session.save(new Employee(userName, age));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   //Added a method to delete.
   

   public static void deleteEmployees(String name, String age) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
        
         // Create SQL delete statement
         String sql = "DELETE FROM employee WHERE name = :name AND age = :age";
        
         // Create SQLQuery object
         SQLQuery query = session.createSQLQuery(sql);
        
         // Set parameters
         query.setParameter("name", name);
         query.setParameter("age", age);
        
         // Execute the delete statement
         int rowCount = query.executeUpdate();
        
         // Commit transaction
         tx.commit();
        
         // Provide feedback
         System.out.println(rowCount + " employee(s) deleted with name " + name + " and age " + age);
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }

   //End
}

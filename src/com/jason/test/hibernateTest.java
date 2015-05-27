package com.jason.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jason.dto.User;

public class hibernateTest {

	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = null;
		Transaction t = null;

		try {
			// 准备数据
			User um = new User();
			um.setUid(998);
			um.setIp("1111111");
//			um.setKid(1);
			

			s = sf.openSession();
			t = s.beginTransaction();
			s.save(um);
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
	}

}

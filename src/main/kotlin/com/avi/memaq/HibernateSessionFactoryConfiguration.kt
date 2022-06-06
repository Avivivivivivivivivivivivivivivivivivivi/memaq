//package com.avi.memaq
//
//import org.hibernate.SessionFactory
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import javax.persistence.EntityManagerFactory
//
//
//@Configuration
//class HibernateSessionFactoryConfiguration{
//  @Bean
//  fun sessionFactory(@Qualifier("entityManagerFactory") emf: EntityManagerFactory): SessionFactory? {
//    return emf.unwrap(SessionFactory::class.java)
//  }
//
//}
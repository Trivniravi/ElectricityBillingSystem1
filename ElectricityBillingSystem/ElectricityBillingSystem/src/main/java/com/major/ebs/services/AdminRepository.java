package com.major.ebs.services;

import com.major.ebs.entity.Admin;
import com.major.ebs.entity.User;

import javax.persistence.*;
import java.util.ArrayList;

public class AdminRepository {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;

    private void start(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Ebs");
        entityManager = entityManagerFactory.createEntityManager();
        transaction   = entityManager.getTransaction();
    }
    public Admin fetchOne(String username){
        Admin[] admin = new Admin[1];
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                admin[0] = entityManager.find(Admin.class,username);
            }
        });
        return admin[0];
    }

    public void setActive(String name){

        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                System.out.println("Merging existing");
                entityManager.merge(new User(name,1));
            }
        });

    }
    public String getActive(){
        String[] name = new String[1];
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                System.out.println("Finding user ...?");
                User user = entityManager.find(User.class, 1);
                if(user!=null)
                    name[0] = user.getName();
                else name[0] = "Admin";
            }
        });
        return name[0];
    }

    private void safeTransaction(Transactor transactor){
        try{
            start();
            transaction.begin();
            transactor.transact();
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public ArrayList<Admin> listAll(){
        ArrayList<Admin> admins = new ArrayList<>();
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                TypedQuery<Admin> query = entityManager.createNamedQuery("Admin.fetchAll",Admin.class);
                admins.addAll(query.getResultList());
            }
        });

        return admins;
    }

    @FunctionalInterface
    interface Transactor{
        void transact();
    }

}

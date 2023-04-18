package com.major.ebs.services;

import com.major.ebs.entity.Customer;

import javax.persistence.*;
import java.util.ArrayList;

public class CustomerRepository {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ebs");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    private void start(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Ebs");
        entityManager = entityManagerFactory.createEntityManager();
        transaction   = entityManager.getTransaction();
    }

    /**
     * Save the Entity of the type specified in the Generic Params
     * Simply pass the populated entity object in the parameter
     * */
    public void save(Customer customer){
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                System.out.println("PERSISTING...");
                entityManager.persist(customer);
            }
        });
    }

    public void delete(int id){
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                Customer customer = entityManager.find(Customer.class, id);
                if(customer!=null){
                    entityManager.remove(customer);
                    transaction.commit();
                }
            }
        });
    }

    public void rechargeUnits(int id, double amount){
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                Customer customer = entityManager.find(Customer.class, id);
                if(customer!=null){
                    customer.updateUnits(amount);

                    entityManager.merge(customer);

                }
            }
        });
    }
    public void update(Customer customer){
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                Customer update = entityManager.find(Customer.class, customer.getId());
                update.setName(customer.getName());
                update.setAddress(customer.getAddress());
                update.setType(customer.getType());
                entityManager.merge(update);
            }
        });
    }
    public ArrayList<Customer> listAll(){
        ArrayList<Customer> customers = new ArrayList<>();
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.fetchAll",Customer.class);
                customers.addAll(query.getResultList());
            }
        });

        return customers;
    }

    public Customer findById(int id){
        Customer[] customers = new Customer[1];
        safeTransaction(new Transactor() {
            @Override
            public void transact() {
                customers[0] = entityManager.find(Customer.class, id);
            }
     });

        return customers[0];
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

    @FunctionalInterface
    interface Transactor{
        void transact();
    }

}

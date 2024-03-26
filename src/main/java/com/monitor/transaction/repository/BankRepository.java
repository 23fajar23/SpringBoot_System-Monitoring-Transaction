package com.monitor.transaction.repository;

import com.monitor.transaction.config.DbConnector;
import com.monitor.transaction.constant.DbPath;
import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BankRepository {
    private final Connection connection = DbConnector.getConnect();

    @Transactional(rollbackOn = Exception.class)
    public Bank create(Bank bank) throws SQLException {
        connection.setAutoCommit(false);
        String query = "INSERT INTO " + DbPath.BANK + " (service,no_rekening,customer_id,id) VALUES (?,?,?,?) ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, bank.getService());
        ps.setString(2, bank.getNoRekening());
        ps.setString(3, bank.getCustomer().getId());
        ps.setString(4, UUID.randomUUID().toString());
        ps.executeUpdate();
        connection.commit();
        return bank;
    }

    @Transactional(rollbackOn = Exception.class)
    public Bank update(Bank bank) throws SQLException {
        if (checkId(bank.getId()))
        {
            connection.setAutoCommit(false);
            String query = "UPDATE " + DbPath.BANK + " SET service = ?, no_rekening = ? WHERE id = ? ";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, bank.getService());
            ps.setString(2, bank.getNoRekening());
            ps.setString(3, bank.getId());
            ps.executeUpdate();
            connection.commit();
            return bank;
        }

        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Bank> findAll() throws SQLException {

        connection.setAutoCommit(false);
        String query = "SELECT * FROM " + DbPath.BANK;

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Bank> banks = new ArrayList<>();
        while (rs.next()){
            Bank bank = Bank.builder()
                    .id(rs.getString("id"))
                    .service(rs.getString("service"))
                    .noRekening(rs.getString("no_rekening"))
                    .customer(findCustomerById(rs.getString("customer_id")))
//                    .transactions()
                    .build();

            banks.add(bank);
        }
        connection.commit();
        return banks;
    }


    @Transactional(rollbackOn = Exception.class)
    public Customer findCustomerById(String id) throws SQLException {
        if (!checkCustomerById(id)){
            return null;
        }

        connection.setAutoCommit(false);
        String query = "SELECT * FROM " + DbPath.CUSTOMER + " WHERE id = ? ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Customer customer = new Customer();
        while (rs.next()){
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setAddress(rs.getString("address"));
        }
        connection.commit();

        return customer;
    }

    @Transactional(rollbackOn = Exception.class)
    public Bank findById(String id) throws SQLException {
        if (!checkId(id)){
            return null;
        }

        connection.setAutoCommit(false);
        String query = "SELECT * FROM " + DbPath.BANK + " WHERE id = ? ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Bank bank = new Bank();
        while (rs.next()){
            bank.setId(rs.getString("id"));
            bank.setService(rs.getString("service"));
            bank.setNoRekening(rs.getString("no_rekening"));
            bank.setCustomer(findCustomerById(rs.getString("customer_id")));
//            bank.setTransactions();
        }
        connection.commit();
        return bank;
    }

    @Transactional(rollbackOn = Exception.class)
    public Boolean checkId(String id){
        boolean foundId = false;
        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM " + DbPath.BANK;
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("id").equals(id)){
                    foundId = true;
                    break;
                }
            }
            connection.commit();

        }catch (SQLException e){
            System.out.println("Failed to fetch data");
        }

        return foundId;
    }

    @Transactional(rollbackOn = Exception.class)
    public Boolean checkCustomerById(String id){
        boolean foundId = false;
        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM " + DbPath.CUSTOMER;
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("id").equals(id)){
                    foundId = true;
                    break;
                }
            }
            connection.commit();
        }catch (SQLException e){
            System.out.println("Failed to fetch data");
        }

        return foundId;
    }
}

package com.monitor.transaction.repository;

import com.monitor.transaction.config.DbConnector;
import com.monitor.transaction.constant.DbPath;
import com.monitor.transaction.constant.EType;
import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.entity.Customer;
import com.monitor.transaction.model.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface BankRepository {
    Connection connection = DbConnector.getConnect();

    @Transactional(rollbackOn = Exception.class)
    default Bank createBank(Bank bank) throws SQLException {
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
    default Bank updateBank(Bank bank) throws SQLException {
        if (checkBankId(bank.getId()))
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
    default Transaction createTransaction(Transaction transaction) throws SQLException {
        connection.setAutoCommit(false);
        String query = "INSERT INTO " + DbPath.TRANSACTION + " (trans_date,type,description,nominal,saldo,bank_id,id) VALUES (?,?,?,?,?,?,?) ";

        String idTransaction = UUID.randomUUID().toString();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, transaction.getTransDate());
        ps.setString(2, String.valueOf(transaction.getType()));
        ps.setString(3, transaction.getDescription());
        ps.setLong(4, transaction.getNominal());
        ps.setLong(5, transaction.getSaldo());
        ps.setString(6, transaction.getBank().getId());
        ps.setString(7, idTransaction);
        ps.executeUpdate();
        connection.commit();

        transaction.setId(idTransaction);
        return transaction;
    }

    @Transactional(rollbackOn = Exception.class)
    default Transaction updateTransaction(Transaction transaction) throws SQLException {

        if (checkTransactionId(transaction.getId()))
        {
            connection.setAutoCommit(false);
            String query = "UPDATE " + DbPath.TRANSACTION + " SET type = ?,nominal = ?, saldo = ?, trans_date = ?, description = ? WHERE id = ? ";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, transaction.getType().toString());
            ps.setLong(2, transaction.getNominal());
            ps.setLong(3, transaction.getSaldo());
            ps.setString(4, transaction.getTransDate());
            ps.setString(5, transaction.getDescription());
            ps.setString(6, transaction.getId());
            ps.executeUpdate();
            connection.commit();
            return transaction;
        }

        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    default Transaction findTransactionById(String id) throws SQLException {
        if (!checkTransactionId(id)){
            return null;
        }

        connection.setAutoCommit(false);
        String query = "SELECT * FROM " + DbPath.TRANSACTION + " WHERE id = ? ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Transaction transaction = new Transaction();
        while (rs.next()){
            transaction.setId(rs.getString("id"));
            transaction.setType(EType.valueOf(rs.getString("type")));
            transaction.setDescription(rs.getString("description"));
            transaction.setNominal(rs.getLong("nominal"));
            transaction.setSaldo(rs.getLong("saldo"));
            transaction.setBank(findBankById(rs.getString(("bank_id"))));
        }
        connection.commit();
        return transaction;
    }

    @Transactional(rollbackOn = Exception.class)
    default List<Transaction> findTransactionByBankId(String idBank) throws SQLException {
        if (!checkBankId(idBank)){
            return null;
        }

        connection.setAutoCommit(false);
        String query = "SELECT * FROM " + DbPath.TRANSACTION + " WHERE bank_id = ? ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, idBank);
        ResultSet rs = ps.executeQuery();

        Bank bank = findBankById(idBank);

        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()){
            Transaction transaction = new Transaction();
            transaction.setId(rs.getString("id"));
            transaction.setTransDate(rs.getString("trans_date"));
            transaction.setType(EType.valueOf(rs.getString("type")));
            transaction.setDescription(rs.getString("description"));
            transaction.setNominal(rs.getLong("nominal"));
            transaction.setSaldo(rs.getLong("saldo"));
            transaction.setBank(bank);
            transactions.add(transaction);
        }
        connection.commit();
        return transactions;
    }

    @Transactional(rollbackOn = Exception.class)
    default List<Bank> findAllBank() throws SQLException {

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
                    .build();

            banks.add(bank);
        }
        connection.commit();
        return banks;
    }


    @Transactional(rollbackOn = Exception.class)
    default Customer findCustomerById(String id) throws SQLException {
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
    default Bank findBankById(String id) throws SQLException {
        if (!checkBankId(id)){
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
        }
        connection.commit();
        return bank;
    }

    @Transactional(rollbackOn = Exception.class)
    default Boolean checkBankId(String id){
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
    default Boolean checkTransactionId(String id){
        boolean foundId = false;
        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM " + DbPath.TRANSACTION;
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
    default Boolean checkCustomerById(String id){
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

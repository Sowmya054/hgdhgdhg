package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;

@Component(value = "service")
public class WalletServiceImpl implements WalletService {
	long millis = System.currentTimeMillis();
	@Autowired
	private WalletRepo repo;

	public Customer createAccount(Customer customer) {
		
		
		
		return repo.save(customer);
	}

	public Customer showBalance(String mobileNo) {
		
		
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
		
		return customer;
		else
			throw new InvalidInputException("Account with given mobile number doesn't exists");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {

		Customer sourceCustomer = repo.findOne(sourceMobileNo);
		Customer destCustomer = repo.findOne(targetMobileNo);
		
		if(sourceCustomer==null && destCustomer==null)
			throw new InvalidInputException("Accounts with given source mobile and destination mobile number doesn't exists");	
		else if(sourceCustomer==null)
			throw new InvalidInputException("Account with given source mobile number doesn't exists");
		else if(destCustomer==null)
			throw new InvalidInputException("Account with given destination mobile number doesn't exists");
		else {
		Wallet balance1 = sourceCustomer.getWallet();
		Wallet balance2 = destCustomer.getWallet();
		if(amount.compareTo(balance1.getBalance())==1)
			throw new InsufficientBalanceException("Insufficient Funds to Transfer");
            
		balance1.setBalance(balance1.getBalance().subtract(amount));
		balance2.setBalance(balance2.getBalance().add(amount));


		repo.save(destCustomer);

		return repo.save(sourceCustomer);}

	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		if(customer!=null) {
		Wallet balance = customer.getWallet();
		balance.setBalance(balance.getBalance().add(amount));
		return repo.save(customer);}
		else
			throw new InvalidInputException("Account with given mobile number doesn't exists");
		
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		if(customer!=null) {
		Wallet balance = customer.getWallet();
		if(amount.compareTo(balance.getBalance())==1)
			throw new InsufficientBalanceException("Insufficient Funds to withdraw");
			
		balance.setBalance(balance.getBalance().subtract(amount));
		return repo.save(customer);}
		else
			throw new InvalidInputException("Account with given mobile number doesn't exists");
		
	}

	private boolean isValidMobile(String mobileNo) {
		if (String.valueOf(mobileNo).matches("[1-9][0-9]{9}")) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public ArrayList<Transaction> miniStatement(String mobileno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Customer> viewALL() {
		// TODO Auto-generated method stub
		return (ArrayList<Customer>) repo.findAll();
	}

	@Override
	public ArrayList<Customer> MinBalCustomers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}

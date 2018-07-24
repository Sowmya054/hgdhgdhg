package com.cg.mypaymentapp.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.service.WalletService;

@Controller
public class CustomerActionController {

	@Autowired
	WalletService walletService;

	@RequestMapping(value = "/registerCustomer")
	public ModelAndView registerCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
		try {
			if (result.hasErrors())
				return new ModelAndView("registrationPage");
			customer = walletService.createAccount(customer);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("errorPage");
		}
		return new ModelAndView("registrationSuccessPage", "customer", customer);
	}

	@RequestMapping(value = "/displayBalancePage")
	public ModelAndView getMobileNumber(@RequestParam("mobileNo") String mobileNo) {
		try {
			Customer customer = walletService.showBalance(mobileNo);
			return new ModelAndView("displayBalancePage", "customer", customer);
		} catch (Exception e) {
			return new ModelAndView("requestBalancePage", "errorMessage", e.getMessage());
		}
	}

	@RequestMapping(value = "/successfullDepositPage")
	public ModelAndView getDeposit(@RequestParam("mobileNo") String mobileNo,
			@RequestParam("wallet.balance") BigDecimal amount) {
		try {
			Customer customer = walletService.depositAmount(mobileNo, amount);

			return new ModelAndView("successfullDepositPage", "customer", customer);
		} catch (Exception e) {
			return new ModelAndView("depositPage", "errorMessage", e.getMessage());
		}
	}

	@RequestMapping(value = "/successfullWithdrawPage")
	public ModelAndView getWithdraw(@RequestParam("mobileNo") String mobileNo,
			@RequestParam("wallet.balance") BigDecimal amount) {
		try {
			Customer customer = walletService.withdrawAmount(mobileNo, amount);

			return new ModelAndView("successfullWithdrawPage", "customer", customer);
		} catch (Exception e) {
			return new ModelAndView("withdrawPage", "errorMessage", e.getMessage());
		}
	}

	@RequestMapping(value = "/successfullFundTransferPage")
	public ModelAndView getFundTransfer(@RequestParam("sourceMobileNo") String sourceMobileNo,
			@RequestParam("targetMobileNo") String targetMobileNo, @RequestParam("wallet.balance") BigDecimal amount) {
		try{Customer customer = walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount);

		return new ModelAndView("successfullFundTransferPage", "customer", customer);}
		catch (Exception e) {
			return new ModelAndView("fundTransferPage", "errorMessage", e.getMessage());
		}
	}

	@RequestMapping(value = "/viewAllPage")
	public ModelAndView getviewAll() {
		ArrayList<Customer> customer = walletService.viewALL();

		return new ModelAndView("viewAllPage", "customer", customer);
	}

}

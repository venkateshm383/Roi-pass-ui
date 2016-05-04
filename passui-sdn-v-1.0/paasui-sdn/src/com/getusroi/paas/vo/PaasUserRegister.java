package com.getusroi.paas.vo;

/**
 * This is a pojo class for PAAS User register
 * @author bizruntime
 *
 */
public class PaasUserRegister {
	private String company_name;
	private String company_address;
	private String email;
	private String password;
	
	
	public PaasUserRegister(String company_name, String company_address,
			String email, String password) {	
		this.company_name = company_name;
		this.company_address = company_address;
		this.email = email;
		this.password = password;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "PaasUserRegisterVO [company_name=" + company_name
				+ ", company_address=" + company_address + ", email=" + email
				+ ", password=" + password + "]";
	}
	
}

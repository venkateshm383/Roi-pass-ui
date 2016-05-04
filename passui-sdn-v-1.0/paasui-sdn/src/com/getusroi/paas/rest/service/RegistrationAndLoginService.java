package com.getusroi.paas.rest.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.PaasUserRegisterAndLoginDAO;
import com.getusroi.paas.rest.service.exception.UserRegisterAndLoginServiceException;
import com.getusroi.paas.vo.PaasUserRegister;


@Path("/registerAndLoginService")
public class RegistrationAndLoginService {
	 static final Logger logger = LoggerFactory.getLogger(RegistrationAndLoginService.class);

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerUser(String registrationData) throws DataBaseOperationFailedException, UserRegisterAndLoginServiceException{
		logger.debug(".registerUser method of RegistrationAndLoginService");
		PaasUserRegisterAndLoginDAO registerDAO=new PaasUserRegisterAndLoginDAO();
		ObjectMapper mapper = new ObjectMapper();
		PaasUserRegister paasUserRegister=null;
		try {
			if(registrationData !=null && !(registrationData.isEmpty()) && !(registrationData.equalsIgnoreCase(""))){
			paasUserRegister = mapper.readValue(registrationData,PaasUserRegister.class);
			registerDAO.registerPaasUser(paasUserRegister);
			}else{
				//throw exception
				throw new UserRegisterAndLoginServiceException("registration data is not valid :  "+registrationData);
			}
		} catch (IOException e) {
			logger.error("Error in reading value from : "+registrationData+" using Object mapper in registerUser ");
			throw new UserRegisterAndLoginServiceException("Error in reading value from : "+registrationData+" using Object mapper in registerUser ");
		}
		
	}//end of method registerUser
	
	
	@GET
	@Path("{Param1}/{Param2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkForUniqueUser(@PathParam("Param1") String username,@PathParam("Param2") String password) throws DataBaseOperationFailedException {
		logger.debug(".checkForUniqueUser method of RegistrationAndLoginService");
		PaasUserRegisterAndLoginDAO checkUniqueUser=new PaasUserRegisterAndLoginDAO();		
		boolean uniqueFlag=checkUniqueUser.userWithEmailPasswordExist(username, password);
		if(uniqueFlag==true){
			return "user : "+username+" and password : "+password+" combination already exist ";
		}else{
		return password;
		}
	}//end of method checkForUniqueUser
	
	@GET
	@Path("{Param1}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkEmailExist(@PathParam("Param1") String username) throws DataBaseOperationFailedException {
		logger.debug(".checkForUniqueUser method of RegistrationAndLoginService");
		PaasUserRegisterAndLoginDAO checkEmailExist=new PaasUserRegisterAndLoginDAO();		
		boolean uniqueFlag=checkEmailExist.checkEmailExist(username);
		if(uniqueFlag==true){
			return "email already exist : "+username;
		}else{
		return username;
		}
	}//end of method checkEmailExist

}

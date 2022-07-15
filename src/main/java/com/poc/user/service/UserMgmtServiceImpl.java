package com.poc.user.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.user.bindings.LoginForm;
import com.poc.user.bindings.UnlockAccForm;
import com.poc.user.bindings.UserRegForm;
import com.poc.user.entity.City_Master;
import com.poc.user.entity.Country_Master;
import com.poc.user.entity.State_Master;
import com.poc.user.entity.User;
import com.poc.user.repository.CityMasterRepo;
import com.poc.user.repository.CountryMasterRepo;
import com.poc.user.repository.StateMasterRepo;
import com.poc.user.repository.UserDtlsRepo;
import com.poc.user.util.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserDtlsRepo userRepo;
	@Autowired
	private CountryMasterRepo countryRepo;
	@Autowired
	private StateMasterRepo stateRepo;
	@Autowired
	private CityMasterRepo cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String logIn(LoginForm loginForm) {

		User user = userRepo.getUserByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
		if (user == null) {
			return "Invalid Credentials";
		}
		if (user != null && user.getAccStatus().equals("LOCKED")) {
			return "Your Account Is Locked";
		}

		return "SUCCESS";
	}

	@Override
	public String checkUniqEmail(String emailId) {

		User user = userRepo.getUserByEmail(emailId);
		if (user == null) {
			return "UNIQUE";
		}

		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> loadCountries() {

		List<Country_Master> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();

		for (Country_Master country : countries) {
			countryMap.put(country.getCountryId(), country.getCountryName());
		}

		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStates(Integer country_Id) {

		List<State_Master> states = stateRepo.getStatesByCountryId(country_Id);
		Map<Integer, String> statesMap = new HashMap<>();

		for (State_Master state : states) {
			statesMap.put(state.getStateId(), state.getStateName());
		}

		return statesMap;
	}

	@Override
	public Map<Integer, String> loadCities(Integer state_Id) {

		List<City_Master> cities = cityRepo.getCitiesByStateId(state_Id);
		Map<Integer, String> cityMap = new HashMap<>();

		for (City_Master city : cities) {
			cityMap.put(city.getCityId(), city.getCityName());
		}

		return cityMap;
	}

	@Override
	public String registerUser(UserRegForm regForm) {

		User user = new User();
		BeanUtils.copyProperties(regForm, user); //copying filed to field from source(regForm) to target(user)
		user.setPassword(generateTempPwd());
		user.setAccStatus("LOCKED");
		User savedEntity = userRepo.save(user);
		// TODO Email for Unlock account
		String email = regForm.getEmail();
		String subject = "User_Registration -Unlock Account";
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBodyContent(fileName, savedEntity);
		boolean isSent = emailUtils.sendMail(email, subject, body);

		if (savedEntity.getUserId() != null  && isSent) {
			return "SUCCESS";
		}

		return "FAIL";
	}

	private String generateTempPwd() {

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockForm) {

		if (!(unlockForm.getNewPwd().equals(unlockForm.getConfirmNewPwd()))) {
			return "New Password and Confirm Password should be same";
		}
		User user = userRepo.getUserByEmailAndPassword(unlockForm.getEmail(), unlockForm.getTempPwd());
		if (user == null) {
			return "Incorrect Temporary Password";
		}
		user.setPassword(unlockForm.getNewPwd());
		user.setAccStatus("UNLOCKED");
		userRepo.save(user); // this will update pwd and acc_status

		return "SUCCESS..! Account Unlocked, please proceed with login";
	}

	@Override
	public String getForgetPwd(String emailId) {

		User user = userRepo.getUserByEmail(emailId);
		if (user == null) {
			return "User not registered with this Email";
		}
		// TODO:GET Pwd and Send Email
		String fileName = "RECOVER-PWD-EMAIL-BODY-TEMPLATE.txt";
		String subject = "RECOVER-PASSWORD";
		String body = readMailBodyContent(fileName, user);
		boolean sendMail = emailUtils.sendMail(emailId, subject, body);
		if(sendMail) {
			return "SUCCESS";
		}
		return "ERROR";

	}

	private String readMailBodyContent(String fileName, User user) {
		String mailBody = null;
		StringBuffer sb = new StringBuffer();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			mailBody = sb.toString();
			mailBody = mailBody.replace("{FNAME}", user.getFirstName());
			mailBody = mailBody.replace("{LNAME}", user.getLastName());
			mailBody = mailBody.replace("{TEMP-PWD}", user.getPassword());
			mailBody = mailBody.replace("{EMAIL}", user.getEmail());
			mailBody = mailBody.replace("{PWD}", user.getPassword());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;
	}

}

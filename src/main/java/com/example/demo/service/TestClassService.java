package com.example.demo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.TestClass;
import com.example.demo.exception.AadharCardNumberAlreadyExistException;
import com.example.demo.respository.TestClassRepository;
import com.example.demoutil.ReflectionUtil;

@Service
public class TestClassService {

	@Autowired
	private TestClassRepository testClassRepository;

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	public TestClass createTestClass(TestClass testClass) {
		try {
			TestClass testClassToDB = testClassRepository.save(testClass);
			return testClassToDB;
		} catch (Exception e) {
			throw new AadharCardNumberAlreadyExistException(
					"Sorry AadharCard Number :" + testClass.getAadharCardNumber() + " " + "Already Exist");
		}
	}

	public TestClass getTestClassById(Long id) {
		TestClass testClass = testClassRepository.getTestClassById(id);
		if (testClass == null) {
			throw new AadharCardNumberAlreadyExistException(
					"Sorry AadharCard Number :" + testClass.getAadharCardNumber() + " " + "Doestn't Exist");
		}
		return testClass;
	}

	public List<TestClass> getAllTestClass() {
		List<TestClass> testClassList = testClassRepository.getAllTestClass();
		if (testClassList == null || testClassList.size() == 0) {
			throw new AadharCardNumberAlreadyExistException("Sorry AadharCard Number No Data Exist");
		}
		return testClassList;
	}

	public String deleteTestClassByID(Long id) {
		TestClass testClass = testClassRepository.getTestClassById(id);
		if (testClass == null) {
			throw new AadharCardNumberAlreadyExistException(
					"Sorry AadharCard Number :" + testClass.getAadharCardNumber() + " " + "Doestn't Exist");
		}
		testClassRepository.deleteById(id);
		return "SuccessFully Deleted";
	}

	public TestClass updateTestClassById(Long id, String testClass) {
		TestClass testClassFromDB = testClassRepository.getTestClassById(id);
		if (testClass == null) {
			throw new AadharCardNumberAlreadyExistException("Sorry No Data Exist For This ID:- " + id);
		}
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(testClass);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String props = (String) iterator.next();
				refUtil.getSetterMethod("TestClass", props).invoke(testClassFromDB, obj.get(props));
			}
			TestClass testClassResponse = testClassRepository.save(testClassFromDB);
			return testClassResponse;

		} catch (ParseException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new AadharCardNumberAlreadyExistException("Sorry TestClass with Id :- " + id + "Doestn't Exist");
		}
	}
}

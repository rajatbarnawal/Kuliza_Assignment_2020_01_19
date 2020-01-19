package com.example.demo.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Father;
import com.example.demo.exception.BaseException;
import com.example.demo.respository.FatherRepository;

@Service

public class FatherService {

	private static final Logger logger = LoggerFactory.getLogger(FatherService.class);

	@Autowired
	public FatherRepository fatherRepository;

	public Father registerFather(Father father) {
		logger.info("**************Inside FatherService in registerFather method*****************");
		try {
			logger.info("**************Inside registerFather try******************");
			Father fatherToDb = fatherRepository.save(father);
			if(fatherToDb == null) {
				throw new BaseException("COULD NOT DAAVEGHIGHOIRWHGIRSGRWGRW");
			}

			logger.info("Father value: " + fatherToDb);
			return fatherToDb;
		} catch (final BaseException e) {
			logger.error("************Inside registerFather Catch Exception***************");
			throw new BaseException("Exception Caught:" + e.getMessage());

		}
	}

	public Father getFatherById(Long fatherId, String name) {
		logger.info("****************Inside FatherService in getFatherById method*****************");
		try {
			logger.info("*****************Inside getFatherById try******************");
			Father fatherFromDb = fatherRepository.fatherById(fatherId, name);
			logger.info("Father value:" + fatherFromDb);
			return fatherFromDb;
		} catch (final Exception e) {
			logger.error("*************Inside getFatherById catch exception*******************");
			throw new HibernateException("Sorry, No Father with id : and fatherName:" + fatherId + name);
		}

	}

	public List<Father> getAllFathers() {
		logger.info("***************Inside FatherService in getAllFathers method****************");
		try {
			logger.info("******************Inside getAllFathers try********************");
			List<Father> fatherFromDb = fatherRepository.getAllFathers();
			logger.info("Father value:" + fatherFromDb);
			return fatherFromDb;

		} catch (final Exception e) {
			logger.error("************Inside getAllFathers catch exception**********");
			throw new HibernateException("Sorry,No father found");
		}
	}

	public String deleteFatherById(Long fatherId) {
		logger.info("**********Inside FatherService in deleteFatherById method***********");
		try {
			logger.info("*************Inside deleteFatherById try**********");
			fatherRepository.deleteById(fatherId);
			logger.info("Father value:");
			return "Successfully Deleted";
		} catch (final Exception e) {
			logger.error("************Inside deleteFatherById catch exception***********");
			throw new HibernateException("Sorry, No father found");
		}
	}

	public Father getFatherByFatherIdOnly(Long id) {
		logger.info("************Inside FatherService in getFatherByFatherIdOnly method************** ");
		try {
			logger.info("************Inside getFatherByFatherIdOnly try*************** ");
			Father fatherFromDB = fatherRepository.fatherByIdOnly(id);
			logger.info("Father value: " + fatherFromDB);
			return fatherFromDB;
		} 
		catch (final Exception e) {
			logger.error("************Inside getFatherByFatherIdOnly catch exception***************");
			throw new RuntimeException(e.getMessage());
		}

	}

}

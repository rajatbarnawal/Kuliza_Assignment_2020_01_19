package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.Daughter;
import com.example.demo.beans.Father;
import com.example.demo.beans.Son;
import com.example.demo.respository.DaughterRepository;
import com.example.demo.respository.SonRepository;
import com.example.demo.service.FatherService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api/object/father")

public class FatherController {
	private static final Logger logger = LoggerFactory.getLogger(FatherController.class);

	@Autowired
	private FatherService fatherService;

	@Autowired
	private SonRepository sonRepository;

	@Autowired
	private DaughterRepository daughterRepository;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createInstance(@Valid @RequestBody Father father, BindingResult bindingResult) {
		logger.info("******************Inside FatherController Create Instance Method**********************");
		logger.info("hellow" + father);
		if (bindingResult.hasErrors() == true) {
			logger.info("****************Inside if block of Father createInstance****************");
			Map<String, String> error = new LinkedHashMap<String, String>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				logger.info("****************Inside for block of Father createInstance**********************");
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);

		}
		logger.info("dob" + father.getFdob());
		Father fatherToDb = fatherService.registerFather(father);
		logger.info("Father value: " + fatherToDb);

		Gson gson = new Gson();
		String response = gson.toJson(fatherToDb).toString();
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/get")
	@ResponseBody
	public ResponseEntity<?> getFatherByID(@RequestParam(value = "id", required = true) Long id) {
		logger.info("***************Inside FatherController getFatherByID method*************** ");
		Father fatherFromDB = fatherService.getFatherByFatherIdOnly(id);
		logger.info("Father value: " + fatherFromDB);
		if (fatherFromDB == null) {
			logger.info("**************Inside if block of father getFatherByID***************");
			return new ResponseEntity<String>("Sorry Could Not Retrieve Data From Father Table For id:- " + id,
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Father>(fatherFromDB, HttpStatus.OK);
	}

	@GetMapping(path = "/get1")
	@ResponseBody
	public ResponseEntity<?> getFatherByIdAndName(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "name", required = true) String name) {
		logger.info("***************Inside FatherController getFatherByIdAndName method*************** ");
		Father fatherFromDB = fatherService.getFatherById(id, name);
		logger.info("Father value: " + fatherFromDB);
		if (fatherFromDB == null) {
			logger.info("**************Inside if block of father getFatherByIdAndName***************");
			return new ResponseEntity<String>("Sorry No Data Found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Father>(fatherFromDB, HttpStatus.OK);

	}

	@GetMapping(path = "/get2")
	@ResponseBody
	public ResponseEntity<?> getAllFatherDetails() {
		logger.info("***************Inside FatherController getAllFatherDetails method*************** ");
		List<Father> fatherList = fatherService.getAllFathers();
		logger.info("Father value: " + fatherList);
		if (fatherList.size() == 0 || fatherList == null) {
			logger.info("**************Inside if block of father getAllFatherDetails***************");
			return new ResponseEntity<String>("Sorry No Data Found For Father", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Father>>(fatherList, HttpStatus.OK);

	}

	@DeleteMapping(path = "/delete")
	@ResponseBody
	public ResponseEntity<?> deleteFatherByID(@RequestParam(value = "id", required = true) Long id) {
		logger.info("***************Inside FatherController deleteFatherByID method*************** ");

		List<Son> allSonForThisFather = sonRepository.getAllSonByFatherId(id);
		for (int iter = 0; iter < allSonForThisFather.size(); iter++) {
			Son sonFromDB = allSonForThisFather.get(iter);
			sonRepository.updateSonDetails(sonFromDB.getId());

		}

		List<Daughter> allDaughterForThisFather = daughterRepository.getAllDaughterByFatherId(id);
		for (int iter = 0; iter < allDaughterForThisFather.size(); iter++) {
			Daughter sonFromDB = allDaughterForThisFather.get(iter);
			daughterRepository.updateDaughterDetails(sonFromDB.getId());
		}

		String response = fatherService.deleteFatherById(id);
		logger.info("Father value: " + response);
		if (response == null) {
			logger.info("**************Inside if block of father deleteFatherByID***************");
			return new ResponseEntity<String>("Sorry No Data Found To Delete", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@PatchMapping(path = "/update")
	@ResponseBody
	public ResponseEntity<?> updateFatherByID(@RequestBody String father,
			@RequestParam(value = "id", required = true) Long id) throws JsonParseException, JsonMappingException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, ParseException {

		Father updatedFather = fatherService.updateFatherById(father, id);
		if (updatedFather == null) {
			return new ResponseEntity<String>("Sorry No Data exist for id:-  " + id, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Father>(updatedFather, HttpStatus.OK);

	}
}

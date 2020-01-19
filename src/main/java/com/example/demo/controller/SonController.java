package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.Son;
import com.example.demo.service.SonService;

@RestController
@RequestMapping(path = "/api/object/son")
public class SonController {

	private static final Logger logger = LoggerFactory.getLogger(SonController.class);

	@Autowired
	private SonService sonService;

	@PostMapping(path = "/create")
	@ResponseBody
	public ResponseEntity<?> createInstance(@Valid @RequestBody Son son, BindingResult bindingResult) {
		logger.info("***********Inside SonController Create Instance method*************");
		if (bindingResult.hasErrors() == true) {
			logger.info("***********Inside if block of Son createInstance*****************");
			Map<String, String> error = new LinkedHashMap<String, String>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				logger.info("************Inside for block of Son createInstance***************");
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
		}

		Son sonToDb = sonService.registerSon(son);
		logger.info("Son value: " + sonToDb);
		return new ResponseEntity<Son>(sonToDb, HttpStatus.CREATED);
	}

	@GetMapping(path = "/get")
	@ResponseBody
	public ResponseEntity<?> getSonByID(@RequestParam(value = "id", required = true) Long id) {
		logger.info("*************Inside SonController getSonByID method**************");
		Son sonFromDB = sonService.getSonBySonIdOnly(id);
		logger.info("Son value: " + sonFromDB);
		if (sonFromDB == null) {
			logger.info("*****************Inside if block of son getSonByID****************");
			return new ResponseEntity<String>("Sorry, Could not retrive data from son table for id:- " + id,
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Son>(sonFromDB, HttpStatus.OK);

	}

	@GetMapping(path = "/get1")
	@ResponseBody
	public ResponseEntity<?> getSonByIdAndName(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "name", required = true) String name) {
		logger.info("****************Inside SonController getSonByIdAndName method**************");
		Son sonFromDB = sonService.getSonById(id, name);
		logger.info("Son value: " + sonFromDB);
		if (sonFromDB == null) {
			logger.info("***********Inside if block of son getSonByIdAndName***************");
			return new ResponseEntity<String>("Sorry No data found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Son>(sonFromDB, HttpStatus.OK);
	}

	@GetMapping(path = "/get2")
	@ResponseBody
	public ResponseEntity<?> getAllSonDetails() {
		logger.info("************Inside SonController getAllSonDetails method************");
		List<Son> sonList = sonService.getAllSons();
		logger.info("Father alue : " + sonList);
		if (sonList.size() == 0 || sonList == null) {
			logger.info("***************Inside if block of son getAllSonDetails***********");
			return new ResponseEntity<String>("Sorry, No data found for son", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Son>>(sonList, HttpStatus.OK);

	}

	@DeleteMapping(path = "/delete")
	@ResponseBody
	public ResponseEntity<?> deleteSonByID(@RequestParam(value = "id", required = true) Long id) {
		logger.info("************Inside SonController deleteSonByID***************");
		String response = sonService.deleteSonById(id);
		logger.info("Son value: " + response);
		if (response == null) {
			logger.info("************Inside if block of son deleteSonByID*************");
			return new ResponseEntity<String>("Sorry, No data found", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
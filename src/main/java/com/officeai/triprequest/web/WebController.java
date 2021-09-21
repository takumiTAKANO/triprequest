package com.officeai.triprequest.web;

import com.officeai.triprequest.model.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class WebController {
  @CrossOrigin
  @PostMapping(value = "/accommodations", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public AccommodationData checkAccommodation(@RequestBody AccommodationData accommodation) {
    accommodation.check();
    return accommodation;
  }

  @CrossOrigin
  @PostMapping(value = "/dailyallowances", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public DailyAllowanceData checkDailyAllowances(@RequestBody DailyAllowanceData dailyAllowance) {
    dailyAllowance.check();
    return dailyAllowance;
  }

  @CrossOrigin
  @PostMapping(value = "/trains", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public TrainData checkTrain(@RequestBody TrainData train) {
    train.check();
    return train;
  }

  @CrossOrigin
  @PostMapping(value = "/excel", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public DocumentData exportExcel(@RequestBody DocumentData excel) {
    excel.export();
    return excel;
  }

  @CrossOrigin
  @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public DeleteFile safeDelete(@RequestBody DeleteFile deleteFile) {
    deleteFile.delete();
    return deleteFile;
  }
}

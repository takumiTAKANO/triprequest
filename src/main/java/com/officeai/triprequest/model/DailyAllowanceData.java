package com.officeai.triprequest.model;

import com.officeai.triprequest.DailyAllowance;
import com.officeai.triprequest.util.KieServiceClient;
import lombok.Data;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Data
public class DailyAllowanceData {
  String tripClass;
  String date;
  String jobTitle;
  String destination;
  Boolean isOnMove;
  Boolean isOnBusiness;
  String stayClass;
  String transportation;
  Integer roundTripDistance;
  Integer departureHour;
  Integer returnHour;

  Integer dailyAllowanceAmount;
  String dailyAllowanceDescription;

  public void check() {
    DailyAllowance a = new DailyAllowance();
    a.set出張分類(tripClass);

    // dateは"2019-01-01"のような形式を想定
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    a.set日付(fmt.parse(date, pos));

    a.set職名(jobTitle);
    a.set目的地(destination);
    a.set移動日であるか(isOnMove);
    a.set用務日であるか(isOnBusiness);
    a.set滞在分類(stayClass);
    a.set交通手段(transportation);
    a.set往復距離(roundTripDistance);
    a.set出発時刻(departureHour);
    a.set帰着時刻(returnHour);

    KieCommands commandFactory = KieServices.Factory.get().getCommands();
    Command<?> insert = commandFactory.newInsert(a, "dailyAllowance");
    Command<?> fireAllRules = commandFactory.newFireAllRules();
    Command<?> batchCommand = commandFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
    ExecutionResults results = KieServiceClient.execute(batchCommand);
    DailyAllowance resultA = (DailyAllowance) results.getValue("dailyAllowance");

    this.setDailyAllowanceAmount(resultA.get金額());
    this.setDailyAllowanceDescription(resultA.get説明());

    Command<?> dispose = commandFactory.newDispose();
    KieServiceClient.execute(dispose);
  }
}

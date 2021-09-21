package com.officeai.triprequest.model;

import com.officeai.triprequest.Accommodation;
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
public class AccommodationData {
  String tripClass;
  String date;
  String jobTitle;
  String destination;
  Boolean isOnMove;
  Boolean isOnBusiness;
  Boolean isInFlightNight;
  Integer oneWayDistance;
  String stayClass;

  Integer accommodationAmount;
  Boolean accommodationIsReasonStatementNecessary;
  String accommodationDescription;

  public void check() {
    Accommodation a = new Accommodation();
    a.set出張分類(tripClass);

    // dateは"2019-01-01"のような形式を想定
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    a.set日付(fmt.parse(date, pos));

    a.set職名(jobTitle);
    a.set目的地(destination);
    a.set移動日であるか(isOnMove);
    a.set用務日であるか(isOnBusiness);
    a.set機内泊であるか(isInFlightNight);
    a.set片道距離(oneWayDistance);
    a.set滞在分類(stayClass);


    KieCommands commandFactory = KieServices.Factory.get().getCommands();
    Command<?> insert = commandFactory.newInsert(a, "accommodation");
    Command<?> fireAllRules = commandFactory.newFireAllRules();
    Command<?> batchCommand = commandFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
    ExecutionResults results = KieServiceClient.execute(batchCommand);
    Accommodation resultA = (Accommodation) results.getValue("accommodation");

    this.setAccommodationAmount(resultA.get金額());
    this.setAccommodationIsReasonStatementNecessary(resultA.get理由書が必要であるか());
    this.setAccommodationDescription(resultA.get説明());

    Command<?> dispose = commandFactory.newDispose();
    KieServiceClient.execute(dispose);
  }
}

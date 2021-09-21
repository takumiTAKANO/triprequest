package com.officeai.triprequest.model;

import com.officeai.triprequest.Train;
import com.officeai.triprequest.util.KieServiceClient;
import lombok.Data;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;

import java.util.Arrays;

@Data
public class TrainData {
  String tripClass;
  Boolean isWayToNaritaAirport;
  Integer oneWayDistance;
  Integer distanceForTheSameTrainSection;
  Boolean hasOnlyReservedSeats;
  Boolean isShinkansen;

  String[] trainAvailableSeats;

  public void check() {
    Train t = new Train();
    t.set出張分類(tripClass);
    t.set目的地は成田空港かどうか(isWayToNaritaAirport);
    t.set片道距離(oneWayDistance);
    t.set同一列車区間距離(distanceForTheSameTrainSection);
    t.set全席指定かどうか(hasOnlyReservedSeats);
    t.set新幹線かどうか(isShinkansen);

    KieCommands commandFactory = KieServices.Factory.get().getCommands();
    Command<?> insert = commandFactory.newInsert(t, "train");
    Command<?> fireAllRules = commandFactory.newFireAllRules();
    Command<?> batchCommand = commandFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
    ExecutionResults results = KieServiceClient.execute(batchCommand);
    Train resultT = (Train) results.getValue("train");

    String availableSeatsText = resultT.get加算できる座席料金();
    if (availableSeatsText == null) {
      this.setTrainAvailableSeats(new String[0]);
    } else {
      this.setTrainAvailableSeats(availableSeatsText.split(","));
    }

    Command<?> dispose = commandFactory.newDispose();
    KieServiceClient.execute(dispose);
  }
}

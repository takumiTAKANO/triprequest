package com.officeai.triprequest.model;

import lombok.Data;

@Data
public class Result {
    String month;
    String date;
    String body;
    Integer fare;
    String payment;
    Integer accommodation;
    Integer dailyAllowance;
}

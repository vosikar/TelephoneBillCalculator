package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Calculator implements TelephoneBillCalculator{

    private final SimpleDateFormat parser;

    public Calculator(){
        parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @Override
    public BigDecimal calculate(String phoneLog){
        Map<Long, PhoneRecord> phoneRecords = new HashMap<>();
        String[] entries = phoneLog.split("\n");

        for(String row : entries){
            // Parse parameters
            String[] split = row.split(",");
            Date from = parseDate(split[1]);
            if(from == null)
                continue;
            Date to = parseDate(split[2]);
            if(to == null)
                continue;
            long phoneNumber = Long.parseLong(split[0].substring(3));

            int totalMinutes = 0;
            double currentCost = 0;
            // Iterate through minutes
            while(from.getTime() < to.getTime()){
                double rate = getRate(from, totalMinutes);
                currentCost = (double) Math.round((currentCost + rate) * 100) / 100;
                totalMinutes++;
                from.setTime(from.getTime() + 60 * 1000);
            }

            PhoneRecord record = phoneRecords.getOrDefault(phoneNumber, new PhoneRecord());
            record.addRecord(currentCost);
            phoneRecords.put(phoneNumber, record);

        }

        // Filter phone numbers
        List<Long> mostCalledNumbers = new ArrayList<>();
        int mostCalls = 0;
        for(Map.Entry<Long, PhoneRecord> entry : phoneRecords.entrySet()){
            long number = entry.getKey();
            int calls = entry.getValue().getCalls();
            if(calls == mostCalls){
                mostCalledNumbers.add(number);
            }else if(calls > mostCalls){
                mostCalls = calls;
                mostCalledNumbers = new ArrayList<>();
                mostCalledNumbers.add(number);
            }
        }

        if(mostCalledNumbers.size() > 1){
            long number = 0L;
            double highestArithmeticMean = 0.0;
            for(long phoneNumber : mostCalledNumbers){
                double arithmeticMean = getArithmeticMean(phoneNumber);
                if(arithmeticMean > highestArithmeticMean){
                    number = phoneNumber;
                    highestArithmeticMean = arithmeticMean;
                }
            }
            phoneRecords.remove(number);
        }else{
            phoneRecords.remove(mostCalledNumbers.getFirst());
        }

        double sum = phoneRecords.values().stream().mapToDouble(PhoneRecord::getCost).sum();
        return BigDecimal.valueOf(sum);
    }

    private double getArithmeticMean(long number){
        int total = 0;
        char[] numbers = String.valueOf(number).toCharArray();
        for(char s : numbers){
            total += Integer.parseInt(s+"");
        }
        return (double) total / numbers.length;
    }

    private double getRate(Date date, int minutes){
        if(minutes >= 5) return 0.2;
        int hours = date.getHours();
        return hours >= 8 && hours < 16 ? 1 : 0.5;
    }

    private Date parseDate(String date){
        try{
            return parser.parse(date);
        }catch(ParseException e){
            System.err.println("Unable to parse date "+date);
            return null;
        }
    }
}

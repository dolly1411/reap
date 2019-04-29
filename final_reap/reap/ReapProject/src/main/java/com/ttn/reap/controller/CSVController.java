package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.DateService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CSVController {
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final Object[] FILE_HEADER = {"Date", "Receiver Name", "Receiver Email", "Sender Name", "Sender Email", "Star", "Comment"};
    @Autowired
    BadgeTransactionService badgeTransactionService;
    @Autowired
    DateService dateService;

    public ResponseEntity<Resource> createCSV(@RequestParam("start") String start, @RequestParam("end") String end) throws IOException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(start);
        Date lastDate = dateService.solveDate(end);
        List<BadgeTransaction> transactions = badgeTransactionService.findAllByDateBetween(startDate, lastDate);
        File file = new File("users_new.csv");
        FileWriter out = new FileWriter(file);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);
        csvPrinter.printRecord(FILE_HEADER);
        for (BadgeTransaction badgeTransaction : transactions) {
            List<String> singleRecord = new ArrayList<>();
            singleRecord.add(String.valueOf(badgeTransaction.getDate()));
            singleRecord.add(badgeTransaction.getReceiver().getName());
            singleRecord.add(badgeTransaction.getReceiver().getEmail());
            singleRecord.add(badgeTransaction.getSender().getName());
            singleRecord.add(badgeTransaction.getSender().getEmail());
            singleRecord.add(badgeTransaction.getBadge().toString());
            singleRecord.add(badgeTransaction.getReason());
            csvPrinter.printRecord(singleRecord);
        }
        out.flush();
        out.close();
        csvPrinter.close();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @GetMapping("downloadCSV")
    public ResponseEntity<Resource> downloadCSV(HttpSession session) throws IOException, ParseException {
        return createCSV((String) session.getAttribute("startDate"), (String) session.getAttribute("endDate"));
    }
}
package com.alogmed.clinica.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping("/summary")
    public Map<String, Object> getSummary(){
        Map<String, Object> mock = new HashMap<>();
        mock.put("totalAgendamentos", 42);
        mock.put("totalFinalizadas", 25);
        mock.put("totalCanceladas", 7);
        return mock;
    }

    @GetMapping("/top-doctors")
    public List<Map<String, Object>> getTopDoctors() {
        List<Map<String, Object>> mockList = new ArrayList<>();

        Map<String, Object> doc1 = new HashMap<>();
        doc1.put("medico", "Dr. João");
        doc1.put("totalAtendimentos", 18);

        Map<String, Object> doc2 = new HashMap<>();
        doc2.put("medico", "Dra. Ana");
        doc2.put("totalAtendimentos", 15);

        mockList.add(doc1);
        mockList.add(doc2);

        return mockList;
    }

    @GetMapping("/cancellations")
    public Map<String, Object> getCancellationRate() {
        Map<String, Object> result = new HashMap<>();
        result.put("taxaCancelamento", 16.67); // porcentagem fake
        return result;
    }
}

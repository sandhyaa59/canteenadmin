package org.grow.canteen.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.report.ReportRequest;
import org.grow.canteen.dto.report.ReportResponse;
import org.grow.canteen.services.ReportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ReportController {

    private ReportServices reportServices;

    @Autowired
    public ReportController(ReportServices reportServices) {
        this.reportServices = reportServices;
    }

    @GetMapping(Route.REPORT)
    private ReportResponse getStatisticsReport(@Valid @RequestBody ReportRequest request){
        log.info("Get Statistics Report{{}}",request);
        return reportServices.getStatisticsReports(request);
    }
}

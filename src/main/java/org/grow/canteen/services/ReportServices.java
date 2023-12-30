package org.grow.canteen.services;

import org.grow.canteen.dto.report.ReportRequest;
import org.grow.canteen.dto.report.ReportResponse;

public interface ReportServices {

    ReportResponse getStatisticsReports(ReportRequest request);
}

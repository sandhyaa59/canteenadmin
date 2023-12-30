package org.grow.canteen.dto.report;

import lombok.Setter;

@Setter
public class ReportResponse {

    public double totalOrder;
    public double totalGrandTotal;
    public double totalSubTotal;
    public double totalDiscount;
}

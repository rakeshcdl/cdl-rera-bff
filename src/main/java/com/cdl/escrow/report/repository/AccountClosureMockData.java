package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.BuildPartnerAssestReportDTO;
import com.cdl.escrow.report.dto.BuildPartnerReportDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountClosureMockData {

    public static final List<BuildPartnerReportDTO> DEVELOPERS = List.of(
            new BuildPartnerReportDTO("dev-1", "CODE Decode Labs Pvt. Ltd."),
            new BuildPartnerReportDTO("dev-2", "GreenField Estates"),
            new BuildPartnerReportDTO("dev-3", "Sunrise Group")
    );

    public static final List<BuildPartnerAssestReportDTO> PROJECTS = List.of(
            new BuildPartnerAssestReportDTO("prj-1","dev-1","CDL Heights",
                    "1111111111","2222222222","3333333333","4444444444"),
            new BuildPartnerAssestReportDTO("prj-2","dev-1","CDL Skytower",
                    "5555555555","6666666666",null,null),
            new BuildPartnerAssestReportDTO("prj-3","dev-2","Green Meadows",
                    "7777777777","8888888888","9999999999",null)
    );

    public static Map<String, List<BuildPartnerAssestReportDTO>> projectsByDeveloper() {
        return PROJECTS.stream().collect(Collectors.groupingBy(BuildPartnerAssestReportDTO::developerId));
    }
}

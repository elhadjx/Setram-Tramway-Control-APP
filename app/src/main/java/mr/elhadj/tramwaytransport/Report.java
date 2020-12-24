package mr.elhadj.tramwaytransport;

public class Report {
    private String id;
    private String agent_id;
    private String reportTitle;
    private String reportMessage;

    public Report(String id, String agent_id, String reportTitle, String reportMessage) {
        this.id = id;
        this.agent_id = agent_id;
        this.reportTitle = reportTitle;
        this.reportMessage = reportMessage;
    }

    public Report(String agent_id, String reportTitle, String reportMessage) {
        this.agent_id = agent_id;
        this.reportTitle = reportTitle;
        this.reportMessage = reportMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }
}

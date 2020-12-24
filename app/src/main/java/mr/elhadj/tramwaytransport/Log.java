package mr.elhadj.tramwaytransport;

public class Log {
    private String id;
    private String agent_id;
    private String card_id;
    private String vehicle_id;
    private String stop_id;
    private String destination_id;
    private String act;

    public Log(String id, String agent_id, String card_id, String vehicle_id, String stop_id,String destination_id, String act) {
        this.id = id;
        this.agent_id = agent_id;
        this.card_id = card_id;
        this.vehicle_id = vehicle_id;
        this.stop_id = stop_id;
        this.destination_id = destination_id;
        this.act = act;
    }

    public Log(String agent_id, String card_id, String vehicle_id, String stop_id, String destination_id,String act) {
        this.agent_id = agent_id;
        this.card_id = card_id;
        this.vehicle_id = vehicle_id;
        this.stop_id = stop_id;
        this.destination_id = destination_id;
        this.act = act;
    }

    public Log(String agent_id, String vehicle_id, String stop_id, String destination_id, String act){
        this.agent_id = agent_id;
        this.card_id = "Null";
        this.vehicle_id = vehicle_id;
        this.stop_id = stop_id;
        this.destination_id = destination_id;
        this.act = act;
    }

    public String getId() {
        return id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getStop_id() {
        return stop_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}

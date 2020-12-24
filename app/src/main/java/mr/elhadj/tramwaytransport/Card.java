package mr.elhadj.tramwaytransport;


public class Card {
    private String id;
    private String cardNumber;
    private String isBlacklisted;
    private String expDate;
    private String subscription;

    public Card(String id, String cardNumber, String expDate, String isBlacklisted, String subscription) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.isBlacklisted = isBlacklisted;
        this.expDate = expDate;
        this.subscription = subscription;
    }
    public Card(String cardNumber, String expDate, String subscription) {
        this.cardNumber = cardNumber;
        this.isBlacklisted = "0";
        this.expDate = expDate;
        this.subscription = subscription;
    }

    public Card(String cardNumber, String expDate, String isBlacklisted, String subscription) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.isBlacklisted = isBlacklisted;
        this.subscription = subscription;
    }

    public String getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIsBlacklisted() {
        return isBlacklisted;
    }

    public void setIsBlacklisted(String isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    public String getExpDate() {
        return expDate;
    }


    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}

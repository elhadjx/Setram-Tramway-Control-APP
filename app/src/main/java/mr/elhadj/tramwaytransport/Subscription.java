package mr.elhadj.tramwaytransport;

import androidx.annotation.NonNull;

public class Subscription {

    private String id;
    private String subscription_name;
    private String subscription_delay;

    public Subscription(String subscription_name, String subscription_delay) {
        this.subscription_name = subscription_name;
        this.subscription_delay = subscription_delay;
    }

    public Subscription(String id, String subscription_name, String subscription_delay) {
        this.id = id;
        this.subscription_name = subscription_name;
        this.subscription_delay = subscription_delay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }

    public String getSubscription_delay() {
        return subscription_delay;
    }

    public void setSubscription_delay(String subscription_delay) {
        this.subscription_delay = subscription_delay;
    }

    @NonNull
    @Override
    public String toString() {
        return subscription_name;
    }
}

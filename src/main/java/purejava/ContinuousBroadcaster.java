package purejava;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bl-lia on 11/28/15.
 */
public class ContinuousBroadcaster implements Runnable {

    public interface Listener {
        void listen(String value);
    }

    private final SimpleDateFormat dateFormat;
    private Listener listener;

    public static ContinuousBroadcaster create() {
        return new ContinuousBroadcaster();
    }

    public ContinuousBroadcaster() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void run() {
        try {
            broadcast();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void broadcast() throws InterruptedException {
        while(true) {
            final Calendar now = Calendar.getInstance();
            System.out.println(String.format("Broadcast value: %s", dateFormat.format(now.getTime())));
            if (listener != null) {
                listener.listen(dateFormat.format(now.getTime()));
            }
            Thread.sleep(1000);
        }
    }
}

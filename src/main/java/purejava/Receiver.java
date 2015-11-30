package purejava;

/**
 * Created by bl-lia on 11/28/15.
 */
public class Receiver {

    static final ContinuousBroadcaster.Listener listener = new ContinuousBroadcaster.Listener() {
        @Override
        public void listen(String value) {
            System.out.println(String.format("Received value: %s", value));
        }
    };

    public static void main(String... strings) throws Exception {
        final ContinuousBroadcaster broadcaster = ContinuousBroadcaster.create();
        new Thread(broadcaster).start();
        Thread.sleep(3000);
        broadcaster.setListener(listener);
    }
}

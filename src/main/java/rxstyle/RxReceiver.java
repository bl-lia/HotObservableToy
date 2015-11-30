package rxstyle;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bl-lia on 11/28/15.
 */
public class RxReceiver {

    static final Action1<String> action = new Action1<String>() {
        @Override
        public void call(String value) {
            System.out.println(String.format("Received value: %s", value));
        }
    };

    public static void main(String... strings) throws Exception {
        final RxContinuousBroadcaster broadcaster = RxContinuousBroadcaster.create();
        final Scheduler scheduler = Schedulers.computation();
        broadcaster.start(scheduler);
        Thread.sleep(3000);
        broadcaster.start(scheduler);
        Thread.sleep(3000);
        broadcaster.getSubject().subscribe(action);
        Thread.sleep(3000);
    }
}

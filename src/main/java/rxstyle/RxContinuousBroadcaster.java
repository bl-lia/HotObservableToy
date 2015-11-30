package rxstyle;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import javax.security.auth.Subject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by bl-lia on 11/28/15.
 */
public class RxContinuousBroadcaster {

    private final PublishSubject<String> subject;
    private final SimpleDateFormat dateFormat;
    private boolean willTerminate = false;

    public static RxContinuousBroadcaster create() {
        return new RxContinuousBroadcaster();
    }

    public RxContinuousBroadcaster() {
        subject = PublishSubject.create();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public PublishSubject<String> getSubject() {
        return subject;
    }

    public void terminate() {
        this.willTerminate = true;
    }

    public void start(Scheduler scheduler) {
        broadcast().subscribeOn(scheduler).subscribe(subject);
    }

    private Observable<String> broadcast() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                final int index = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                while(true) {
                    try {
                        if (willTerminate) {
                            subscriber.onCompleted();
                            return;
                        }

                        final Calendar now = Calendar.getInstance();
                        System.out.println(String.format("%s Broadcast value: %s", index, dateFormat.format(now.getTime())));
                        subscriber.onNext("index: " + index + " " + dateFormat.format(now.getTime()));

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }
}

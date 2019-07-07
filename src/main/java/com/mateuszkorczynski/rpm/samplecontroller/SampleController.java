package com.mateuszkorczynski.rpm.samplecontroller;

import com.mateuszkorczynski.rpm.utils.Sleeper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

public class SampleController {
  private Logger log = LoggerFactory.getLogger(SampleController.class);

  private List<String> returnIds;

  private Random random = new Random();

  private List<String> getReturnIds() {
    return returnIds;
  }

  public SampleController() {
    generateIds();
  }

  private void generateIds() {
    Observable.fromCallable(() -> (random.nextInt( 30)))
        .repeat()
        .map(item -> item.toString())
        .take((random.nextInt(8)) + 1)
        .toList()
        .subscribe(
            new Subscriber<List<String>>() {
              @Override
              public void onCompleted() {
                log.info("Got: {}", returnIds);
              }

              @Override
              public void onError(Throwable throwable) {}

              @Override
              public void onNext(List<String> strings) {
                returnIds = strings;
              }
            });
  }


  public String fetchId(String id) {
    log.info("Loading for {}", id);
    final int delay = (random.nextInt(1500)) + 350;
    Sleeper.sleep(Duration.ofMillis(delay));
    //HTTP Mock
    return id + " FETCHED after " + delay +"ms";
  }

  public Observable<String> rxFetchId(String id) {
    return Observable.fromCallable(
        () ->
            fetchId(id));
  }

  public List<String> getIds(String url) {
    log.info("Loading for url {}", url);
    generateIds();
    final int delay = (random.nextInt(1500)) + 350;
    Sleeper.sleep(Duration.ofMillis(delay));
    //HTTP, HTTP, HTTP
    return getReturnIds();
  }

  public Observable<List<String>> rxGetIds(String url) {
    return Observable.fromCallable(() -> getIds(url));
  }

}

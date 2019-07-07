package com.mateuszkorczynski.rpm;

import com.mateuszkorczynski.rpm.samplecontroller.SampleController;
import java.util.concurrent.Executors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.schedulers.Schedulers;

@SpringBootApplication
public class RpmApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpmApplication.class, args);

		SampleController sampleController = new SampleController();

		sampleController.rxGetIds("server1:8080/getIds")
				.flatMapIterable(str -> str)
				.flatMap(
						(String s) -> sampleController.rxFetchId(s)
								.subscribeOn(Schedulers.from(Executors.newFixedThreadPool(20)))
				)
				.repeat()
				.subscribe();

		sampleController.rxGetIds("server2:8080/getIds")
				.flatMapIterable(str -> str)
				.flatMap(
						(String s) -> sampleController.rxFetchId(s)
								.subscribeOn(Schedulers.from(Executors.newFixedThreadPool(20)))
				)
				.repeat()
				.subscribe();

		sampleController.rxGetIds("server3:8080/getIds")
				.flatMapIterable(str -> str)
				.flatMap(
						(String s) -> sampleController.rxFetchId(s)
								.subscribeOn(Schedulers.from(Executors.newFixedThreadPool(20)))
				)
				.repeat()
				.subscribe();

	}
}


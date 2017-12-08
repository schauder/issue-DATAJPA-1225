package de.thd.bugs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ReportApplication extends AsyncConfigurerSupport implements CommandLineRunner {
	@Override
	public void run(String... strings) throws Exception {

	}
}

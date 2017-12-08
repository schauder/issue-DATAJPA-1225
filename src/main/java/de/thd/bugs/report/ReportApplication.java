package de.thd.bugs.report;

import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import de.thd.bugs.report.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ReportApplication extends AsyncConfigurerSupport implements CommandLineRunner {

    @Autowired
    private InvoiceService invoiceService;

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

    /**
     * Hooks up the task executor for async stuff
     *
     * @return Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    @Override
    public void run(String... strings) throws InterruptedException, ExecutionException {
    	/*
        log.error("first, let´s seed a test invoice ... ");
        final Future<String> invoice = invoiceService.invoiceInvoice(Invoice.makeTestInvoice());
        while (!invoice.isDone()) {
            Thread.sleep(100);
        }

        log.error("then do the select ...");
        final Future<Iterable<InvoiceProjection>> invoices = invoiceService.findAllProjectedByUserAndApiGroupsAndInvoiceState("tlang", "%tlang%|%Seminare%", InvoiceState.Invoiced);
        while (!invoices.isDone()) {
            Thread.sleep(100);
        }
        Assert.notNull(invoices.get(), "something went wrong ...");
        final String bookingTagsJoined = StreamSupport.stream(invoices.get().spliterator(), false).map(InvoiceProjection::getBookingTag).collect(Collectors.joining("; "));

		InvoiceProjection next = invoices.get().iterator().next();

		// should be not null
		String bookingTag = next.getBookingTag();

		System.out.println("booking tag:" + bookingTag);

		log.error(bookingTagsJoined);
		*/
    }
}

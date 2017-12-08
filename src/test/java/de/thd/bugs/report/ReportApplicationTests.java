package de.thd.bugs.report;

import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import de.thd.bugs.report.service.IInvoiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportApplicationTests {

	@Autowired
	IInvoiceRepository repository;

	@Test
    public void contextLoads() {

		assertThat(repository).isNotNull();
		repository.save(Invoice.makeTestInvoice());

		Iterable<InvoiceProjection> invoiceProjections = repository.findAllProjectedByInvoiceStateAndSimilarUserOrApiGroup("%tlang%|%Seminare%", InvoiceState.Invoiced.ordinal());

		InvoiceProjection invoiceProjection = invoiceProjections.iterator().next();

		assertThat(invoiceProjection).isNotNull();
		assertThat(invoiceProjection.getBookingTag()).isNotNull();
	}

}

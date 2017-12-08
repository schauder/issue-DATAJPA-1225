package de.thd.bugs.report.service;


import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IInvoiceRepository extends CrudRepository<Invoice, Long> {

    @Query(value = "select i.invoiceid as invoiceId, i.bookingtag as \"bookingTag\" from invoice i ", nativeQuery = true)
    Iterable<InvoiceProjection> findAllNative();


}

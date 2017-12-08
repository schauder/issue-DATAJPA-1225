package de.thd.bugs.report.service;


import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IInvoiceRepository extends CrudRepository<Invoice, Long> {

    @Query(value = "select i.invoiceid as invoiceId, i.bookingtag as bookingTag, i.invoicenumber as invoiceNumber, i.createdby as createdBy, i.invoiceapigroups as apiGroups, i.invoicedate as invoiceDate, r.recipient as recipient from invoice i inner join recipient r on (i.recipient_recipientid = r.recipientid) where i.invoicestate = ?2 and i.invoiceapigroups similar to ?1 order by i.invoicedate desc", nativeQuery = true)
    Iterable<InvoiceProjection> findAllProjectedByInvoiceStateAndSimilarUserOrApiGroup(String similarToPattern, int state);


}

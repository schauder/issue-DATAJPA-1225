package de.thd.bugs.report.service;


import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * A repository access layer for model Invoice.
 * Gets some additional crud functions by extending Spring´s CrudRepository.
 * <p/>
 * Note:
 * Usually there are no implementations for the interfaces,
 * because of spring data jpa and its self implementing mechanism.
 * <p/>
 * seems to work really well.
 *
 * @author tlang
 */
interface IInvoiceRepository extends CrudRepository<Invoice, Long> {

    /**
     * Finds all invoices by a given user login and a given invoice state.
     * Creates a projected model with only the needed values.
     *
     * @param createdBy    the given user login
     * @param invoiceState a given invoice state
     * @return Iterable of InvoiceProjection
     */
    @Query("select i.invoiceId as invoiceId, i.bookingTag as bookingTag, i.invoiceNumber as invoiceNumber, " +
            "i.invoiceDate as invoiceDate, r.recipient as recipient from Invoice i join i.recipient r where i.invoiceState = ?2 and i.information.createdBy = ?1 order by i.invoiceDate desc")
    Iterable<InvoiceProjection> findAllProjectedByInvoiceStateAndUser(String createdBy, InvoiceState invoiceState);

    /**
     * Does a native query to get the invoices assigned for the given users and groups coded in the similarToPattern
     *
     * @param similarToPattern the similar to pattern (postgres specific as "%tlang%|%Seminare%"
     * @param state            a given invoice state
     * @return Iterable of InvoiceProjection
     */
    @Query(value = "select i.invoiceid as invoiceId, i.bookingtag as bookingTag, i.invoicenumber as invoiceNumber, i.createdby as createdBy, i.invoiceapigroups as apiGroups, i.invoicedate as invoiceDate, r.recipient as recipient from invoice i inner join recipient r on (i.recipient_recipientid = r.recipientid) where i.invoicestate = ?2 and i.invoiceapigroups similar to ?1 order by i.invoicedate desc", nativeQuery = true)
    Iterable<InvoiceProjection> findAllProjectedByInvoiceStateAndSimilarUserOrApiGroup(String similarToPattern, int state);


}

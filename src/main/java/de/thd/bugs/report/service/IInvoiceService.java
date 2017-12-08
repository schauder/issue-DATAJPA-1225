package de.thd.bugs.report.service;


import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import lombok.NonNull;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

/**
 * Invoice service layer.
 * Adds some additional business logic on top of the repository
 * layer.
 *
 * @author tlang
 */
public interface IInvoiceService {


    /**
     * Creates a new invoice.
     *
     * @param invoice a given new invoice.
     * @throws Exception a given exception
     */
    @Async
    Future<String> invoiceInvoice(@NonNull Invoice invoice) throws Exception;



    /**
     * Finds all invoices projected by the needed values.
     *
     * @param createdBy    a given user login
     * @param apiGroups    given api groups
     * @param invoiceState a given invoice state
     * @return Future of Iterable of InvoiceProjection
     */
    @Async
    Future<Iterable<InvoiceProjection>> findAllProjectedByUserAndApiGroupsAndInvoiceState(String createdBy,
                                                                                          String apiGroups, InvoiceState invoiceState);


}

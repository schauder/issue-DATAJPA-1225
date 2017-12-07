package de.thd.bugs.report.service;


import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
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

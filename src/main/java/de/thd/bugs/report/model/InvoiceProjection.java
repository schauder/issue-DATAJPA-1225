package de.thd.bugs.report.model;

import java.time.LocalDate;

/**
 * Projection interface for spring data jpa.
 * takes the resultset of a spring data jpa query.
 *
 * @author tlang
 */
public interface InvoiceProjection {

    /**
     * The invoice id.
     *
     * @return long
     */
    long getInvoiceId();

    /**
     * The booking tag.
     *
     * @return String
     */
    String getBookingTag();

}

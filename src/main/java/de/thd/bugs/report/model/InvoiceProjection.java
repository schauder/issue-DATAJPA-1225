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

    /**
     * The invoice number.
     *
     * @return String
     */
    String getInvoiceNumber();

    /**
     * The invoice date.
     *
     * @return LocalDate
     */
    LocalDate getInvoiceDate();

    /**
     * The invoice recipient.
     *
     * @return String
     */
    String getRecipient();

    /**
     * The invoice state
     *
     * @return InvoiceState
     */
    InvoiceState getInvoiceState();

    /**
     * The created by property.
     *
     * @return String
     */
    String getCreatedBy();

    /**
     * The modified by property.
     *
     * @return String
     */
    String getModifiedBy();

    /**
     * The api groups projection.
     *
     * @return String
     */
    String getApiGroups();
}

package de.thd.bugs.report.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo entity for invoice.
 * Stores information concerning the invoice.
 *
 * @author tlang
 */
@Entity
@Table(name = "invoice", schema = "public",
        indexes = {
                @Index(name = "index_bookingTag", columnList = "bookingTag"),
                @Index(name = "index_invoiceApiGroups", columnList = "invoiceApiGroups"),
                @Index(name = "index_invoiceNumber", columnList = "invoiceNumber", unique = true),
                @Index(name = "index_invoiceDate", columnList = "invoiceDate")
        })
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Invoice.full",
                attributeNodes = {
                        @NamedAttributeNode("lineItemSet"),
                        @NamedAttributeNode("invoiceProtocolSet"),
                        @NamedAttributeNode("recipient"),
                        @NamedAttributeNode("biller")
                })
})
@Getter
@Setter
public class Invoice implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @NotNull
    private long invoiceId;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{invoice.bookingTag.notnull.message}")
    @Size(min = 2, max = 50, message = "{invoice.bookingTag.between.message}")
    private String bookingTag;

    @Column(unique = true, nullable = false, length = 25)
    @NotNull(message = "{invoice.invoiceNumber.notnull.message}")
    @Size(min = 5, max = 25, message = "{invoice.invoiceNumber.between.message}")
    private String invoiceNumber;

    @Column()
    @Size(max = 255, message = "{invoice.invoiceMemo.max.message}")
    private String invoiceMemo;

    @Column(nullable = false)
    @NotNull(message = "{invoice.invoiceDate.notnull.message}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate invoiceDate;

    @Column(nullable = false)
    @NotNull(message = "{invoice.serviceDateFrom.notnull.message}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate serviceDateFrom;

    @Column(nullable = false)
    @NotNull(message = "{invoice.serviceDateUntil.notnull.message}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate serviceDateUntil;

    @Column(nullable = false)
    @NotNull(message = "{invoice.accountingConnection.notnull.message}")
    @Size(min = 5, max = 255, message = "{invoice.paymentTerms.between.message}")
    private String accountingConnection;

    @Column(nullable = false, length = 1024)
    @NotNull(message = "{invoice.paymentTerms.notnull.message}")
    @Size(min = 5, max = 1024, message = "{invoice.paymentTerms.between.message}")
    private String paymentTerms;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{invoice.taxId.notnull.message}")
    @Size(min = 5, max = 50, message = "{invoice.taxId.between.message}")
    private String taxId;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{invoice.salesTaxId.notnull.message}")
    @Size(min = 5, max = 50, message = "{invoice.salesTaxId.between.message}")
    private String salesTaxId;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "invoice", fetch = FetchType.LAZY)
    @NotNull(message = "{invoice.lineItemSet.notnull.message}")
    @Valid
    @OrderBy("lineItemNumber")
    private Set<LineItem> lineItemSet;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "invoice", fetch = FetchType.LAZY)
    @OrderBy("invoiceProtocolDate")
    private Set<InvoiceProtocol> invoiceProtocolSet;

    @Column(nullable = false)
    @Embedded
    @NotNull(message = "{information.notnull.message}")
    private Information information;

    @Column(nullable = false)
    @NotNull(message = "{invoice.invoiceApiGroups.notnull.message}")
    @Size(min = 2, message = "{invoice.invoiceApiGroups.min.message}")
    private String invoiceApiGroups;

    @Column(nullable = false)
    @Enumerated
    @NotNull(message = "{invoiceState.notnull.message}")
    private InvoiceState invoiceState;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    @Valid
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    @Valid
    private Biller biller;


    /**
     * Standard Constructor.
     */
    public Invoice() {
        this.lineItemSet = new HashSet<>();
        this.invoiceProtocolSet = new HashSet<>();
        this.information = new Information();
        this.invoiceDate = LocalDate.now();
        this.serviceDateFrom = LocalDate.now();
        this.serviceDateUntil = LocalDate.now();
    }

    /**
     * Helper constructor.
     * Builds a new invoice to be saved later.
     *
     * @param bookingTag           a given booking tag
     * @param invoiceNumber        a given invoice number
     * @param invoiceMemo          a given invoice memo
     * @param invoiceDate          a given invoice date
     * @param serviceDateFrom      a given service date from
     * @param serviceDateUntil     a given service date until
     * @param accountingConnection given accounting connecting
     * @param paymentTerms         given payment terms
     * @param taxId                given tax id
     * @param salesTaxId           given sales tax id
     * @param lineItemSet          a given line set
     * @param invoiceProtocolSet   a given invoice protocol set
     * @param invoiceApiGroups     given invcoice api groups
     * @param invoiceState         a given invoice state
     * @param recipient            a given recipient
     * @param biller               a given biller
     */
    private Invoice(String bookingTag, String invoiceNumber, String invoiceMemo, LocalDate invoiceDate, LocalDate serviceDateFrom,
                    LocalDate serviceDateUntil, String accountingConnection, String paymentTerms, String taxId,
                    String salesTaxId, Set<LineItem> lineItemSet, Set<InvoiceProtocol> invoiceProtocolSet,
                    String invoiceApiGroups, InvoiceState invoiceState, Recipient recipient, Biller biller) {
        this();
        this.bookingTag = bookingTag;
        this.invoiceNumber = invoiceNumber;
        this.invoiceMemo = invoiceMemo;
        this.invoiceDate = invoiceDate;
        this.serviceDateFrom = serviceDateFrom;
        this.serviceDateUntil = serviceDateUntil;
        this.accountingConnection = accountingConnection;
        this.paymentTerms = paymentTerms;
        this.taxId = taxId;
        this.salesTaxId = salesTaxId;
        this.lineItemSet = lineItemSet;
        this.invoiceProtocolSet = invoiceProtocolSet;
        this.invoiceApiGroups = invoiceApiGroups;
        this.invoiceState = invoiceState;
        this.recipient = recipient;
        this.biller = biller;
        this.information.setCreatedBy("tlang");
        this.information.setModifiedBy("tlang");
    }

    /**
     * Makes a test invoice.
     *
     * @return Invoice
     */
    public static Invoice makeTestInvoice() {
        Invoice test = new Invoice("1546.784.5122", "0001-2017", "Testrechnung",
                LocalDate.now(), LocalDate.now().minusDays(2), LocalDate.now().minusDays(1),
                "Kontoverbindung: 123", "Einige Zahlungsmodalitäten",
                "Steuer ID Nummer", "UST ID Nummer", null, null,
                "tlang, Seminare", InvoiceState.Invoiced, null, null);

        test.lineItemSet = new HashSet<>();
        test.lineItemSet.add(LineItem.makeTestLineItem());
        test.lineItemSet.forEach(s -> s.setInvoice(test));

        test.setBiller(Biller.makeTestBiller());
        test.getBiller().getInvoiceSet().add(test);

        test.setRecipient(Recipient.makeTestRecipient());
        test.getRecipient().getInvoiceSet().add(test);

        return test;
    }


    /**
     * Compares this instance with another instance of this.
     *
     * @param o another instance
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceId == invoice.invoiceId;
    }

    /**
     * Computes a hash code for this instance.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(invoiceId);
    }

    /**
     * Formats the given service date.
     *
     * @return String
     */
    public String printServiceDate() {
        if (this.serviceDateUntil == null || serviceDateFrom == null) return "";
        final long daysBetween = ChronoUnit.DAYS.between(serviceDateFrom, serviceDateUntil);
        if (daysBetween < 1) return this.serviceDateFrom.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        return String.format("%s - %s", this.serviceDateFrom.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), this.serviceDateUntil.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }
}
package de.thd.bugs.report.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Pojo entity for invoice.
 * Stores information concerning the invoice.
 *
 * @author tlang
 */
@Entity
@Table(name = "invoiceprotocol", schema = "public",
        indexes = {
                @Index(name = "index_invoiceProtocolUser", columnList = "invoiceProtocolUser")
        })
@Getter
@Setter
public class InvoiceProtocol implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @NotNull
    private long invoiceProtocolId;

    @Column()
    @Size(max = 255, message = "{invoiceProtocol.invoiceProtocolMemo.max.message}")
    private String invoiceProtocolMemo;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{invoiceProtocol.invoiceProtocolUser.notnull.message}")
    @Size(min = 2, max = 50, message = "{invoiceProtocol.invoiceProtocolUser.between.message}")
    private String invoiceProtocolUser;

    @Column(nullable = false)
    @NotNull(message = "{invoiceProtocol.invoiceProtocolDate.notnull.message}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate invoiceProtocolDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{invoiceProtocol.invoice.notnull.message}")
    private Invoice invoice;

    /**
     * Standard constructor.
     * Builds the needed dependencies.
     */
    public InvoiceProtocol() {
        this.invoiceProtocolDate = LocalDate.now();
    }

    /**
     * Overloaded constructor.
     * Builds the needed dependencies.
     *
     * @param login a given login
     */
    public InvoiceProtocol(@NonNull String login) {
        this();
        this.invoiceProtocolUser = login;

    }

    /**
     * Overloaded constructor.
     * Builds the needed dependencies.
     *
     * @param login               a given user login
     * @param invoiceProtocolDate a given protocol date
     * @param invoiceProtocolMemo a given protocol memo
     * @param invoice             a given invoice
     */
    private InvoiceProtocol(String login, LocalDate invoiceProtocolDate, String invoiceProtocolMemo,
                            Invoice invoice) {
        this(login);
        this.invoiceProtocolDate = invoiceProtocolDate;
        this.invoiceProtocolMemo = invoiceProtocolMemo;
        this.invoice = invoice;
    }

    /**
     * Copies the given invoice protocol.
     *
     * @param invoiceProtocol a given invoice protocol
     * @return InvoiceProtocol
     */
    public static InvoiceProtocol copy(@NonNull InvoiceProtocol invoiceProtocol) {
        return new InvoiceProtocol(invoiceProtocol.getInvoiceProtocolUser(), invoiceProtocol.getInvoiceProtocolDate(),
                invoiceProtocol.getInvoiceProtocolMemo(), invoiceProtocol.getInvoice());
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
        InvoiceProtocol that = (InvoiceProtocol) o;
        return invoiceProtocolId == that.invoiceProtocolId;
    }

    /**
     * Computes a hash code for this instance.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(invoiceProtocolId);
    }
}
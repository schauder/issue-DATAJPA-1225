package de.thd.bugs.report.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Pojo entity for application user information.
 * Stores information concerning the application user.
 *
 * @author tlang
 */
@Entity
@Table(name = "lineitem", schema = "public")
@Getter
@Setter
public class LineItem implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @NotNull
    private long lineItemId;

    @Column(nullable = false)
    @NotNull(message = "{lineItem.lineItemNumber.notnull.message}")
    @Min(value = 1, message = "{lineItem.lineItemNumber.min.message}")
    private int lineItemNumber;

    @Column(nullable = false, scale = 2, precision = 10)
    @NotNull(message = "{lineItem.lineItemQuantity.notnull.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal lineItemQuantity;

    @Column(nullable = false, length = 25)
    @NotNull(message = "{lineItem.lineItemUnit.notnull.message}")
    @Size(min = 2, max = 25, message = "{lineItem.lineItemUnit.between.message}")
    private String lineItemUnit;

    @Column(nullable = false, length = 100)
    @NotNull(message = "{lineItem.lineItemText.notnull.message}")
    @Size(min = 2, max = 100, message = "{lineItem.lineItemText.between.message}")
    private String lineItemText;

    @Column(nullable = false, scale = 2, precision = 10)
    @NotNull(message = "{lineItem.lineItemUnitPrice.notnull.message}")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal lineItemUnitPrice;

    @Transient
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Getter(AccessLevel.NONE)
    private BigDecimal lineItemTotalPrice;

    @Transient
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Getter(AccessLevel.NONE)
    private BigDecimal lineItemTotalPriceGross;

    @Column(nullable = false)
    @NotNull(message = "{lineItem.bettermentTaxRate.notnull.message}")
    @Min(value = 0, message = "{lineItem.bettermentTaxRate.min.message}")
    @Max(value = 100, message = "{lineItem.bettermentTaxRate.max.message}")
    private int bettermentTaxRate;

    @Transient
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Getter(AccessLevel.NONE)
    private BigDecimal bettermentTaxValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{lineItem.invoice.notnull.message}")
    private Invoice invoice;


    /**
     * Standard Constructor.
     */
    public LineItem() {
        this.lineItemNumber = 1;
        this.bettermentTaxRate = 0;
        this.lineItemQuantity = new BigDecimal(BigInteger.ONE);
        this.lineItemUnitPrice = new BigDecimal(BigInteger.ZERO);
        this.lineItemTotalPrice = new BigDecimal(BigInteger.ZERO);
    }

    /**
     * Overloaded constructor.
     * Builds up the needed dependencies.
     *
     * @param lineItemNumber    a given line item number
     * @param lineItemQuantity  a given line item quantity
     * @param lineItemUnit      a given line item unit
     * @param lineItemText      a given line item text
     * @param lineItemUnitPrice a given line item unit price
     * @param bettermentTaxRate a given betterment tax rate
     * @param invoice           a given invoice
     */
    public LineItem(int lineItemNumber, @NonNull BigDecimal lineItemQuantity, @NonNull String lineItemUnit,
                    @NonNull String lineItemText, @NonNull BigDecimal lineItemUnitPrice, int bettermentTaxRate,
                    @NonNull Invoice invoice) {
        this();
        this.lineItemNumber = lineItemNumber;
        this.lineItemQuantity = lineItemQuantity;
        this.lineItemUnit = lineItemUnit;
        this.lineItemText = lineItemText;
        this.lineItemUnitPrice = lineItemUnitPrice;
        this.bettermentTaxRate = bettermentTaxRate;
        this.invoice = invoice;
    }

    /**
     * Copies the given line item.
     *
     * @param lineItem a given line item
     * @return LineItem the copied line item
     */
    public static LineItem copy(@NonNull LineItem lineItem) {
        return new LineItem(lineItem.getLineItemNumber(), lineItem.getLineItemQuantity(), lineItem.getLineItemUnit(),
                lineItem.getLineItemText(), lineItem.lineItemUnitPrice, lineItem.getBettermentTaxRate(), lineItem.getInvoice());
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
        LineItem lineItem = (LineItem) o;
        return lineItemId == lineItem.lineItemId &&
                lineItemNumber == lineItem.lineItemNumber;
    }

    /**
     * Computes a hash code for this instance.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(lineItemId, lineItemNumber);
    }

    /**
     * Getter for the line item total price.
     *
     * @return BigDecimal
     */
    public BigDecimal getLineItemTotalPrice() {
        if (this.lineItemTotalPrice == null) return BigDecimal.ZERO;
        return this.lineItemTotalPrice = this.lineItemUnitPrice.multiply(this.lineItemQuantity);
    }

    /**
     * Getter for the betterment tax value.
     *
     * @return BigDecimal
     */
    public BigDecimal getBettermentTaxValue() {
        if (this.lineItemTotalPrice == null) return BigDecimal.ZERO;
        return this.bettermentTaxValue = this.lineItemUnitPrice.multiply(this.lineItemQuantity).multiply(BigDecimal.valueOf(this.bettermentTaxRate).divide(BigDecimal.valueOf(100)));
    }

    /**
     * Getter for the line item total price including taxes.
     *
     * @return BigDecimal
     */
    public BigDecimal getLineItemTotalPriceGross() {
        if (this.lineItemTotalPrice == null) return BigDecimal.ZERO;
        return this.bettermentTaxValue = this.lineItemUnitPrice
                .multiply(this.lineItemQuantity)
                .multiply(BigDecimal.valueOf((100 + this.bettermentTaxRate))).divide(BigDecimal.valueOf(100));
    }
}
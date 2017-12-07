package de.thd.bugs.report.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo entity for application user information.
 * Stores information concerning the application user.
 *
 * @author tlang
 */
@Entity
@Table(name = "recipient", schema = "public",
        indexes = {
                @Index(name = "index_recipient", columnList = "recipient"),
                @Index(name = "index_recipientContactPerson", columnList = "recipientContactPerson"),
                @Index(name = "index_recipientPostalcode", columnList = "recipientPostalcode"),
                @Index(name = "index_recipientCity", columnList = "recipientCity")
        })
@Getter
@Setter
public class Recipient implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @NotNull
    private long recipientId;

    @Column(length = 50)
    @Size(max = 50, message = "{recipient.recipientSalutation.max.message}")
    private String recipientSalutation;

    @Column(nullable = false, length = 150)
    @NotNull(message = "{recipient.recipient.notnull.message}")
    @Size(min = 2, max = 150, message = "{recipient.recipient.between.message}")
    private String recipient;

    @Column(length = 150)
    @Size(max = 150, message = "{recipient.recipientContactPerson.between.message}")
    private String recipientContactPerson;

    @Column(nullable = false, length = 100)
    @NotNull(message = "{recipient.recipientStreet.notnull.message}")
    @Size(min = 2, max = 100, message = "{recipient.recipientStreet.between.message}")
    private String recipientStreet;

    @Column(length = 50)
    @Size(max = 50, message = "{recipient.recipientBox.max.message}")
    private String recipientBox;

    @Column(nullable = false, length = 10)
    @NotNull(message = "{recipient.recipientPostalcode.notnull.message}")
    @Size(min = 2, max = 10, message = "{recipient.recipientPostalcode.between.message}")
    private String recipientPostalcode;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{recipient.recipientCity.notnull.message}")
    @Size(min = 2, max = 50, message = "{recipient.recipientCity.between.message}")
    private String recipientCity;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{recipient.recipientCountry.notnull.message}")
    @Size(min = 2, max = 50, message = "{recipient.recipientCountry.between.message}")
    private String recipientCountry;

    @Column(length = 50)
    @Size(max = 50, message = "{recipient.recipientWeb.max.message}")
    private String recipientWeb;

    @Column(nullable = false)
    @Embedded
    @NotNull(message = "{information.notnull.message}")
    private Information information;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "recipient", fetch = FetchType.LAZY, orphanRemoval = true)
    @NotNull
    private Set<Invoice> invoiceSet;

    /**
     * Standard Constructor.
     */
    public Recipient() {
        this.invoiceSet = new HashSet<>();
        this.information = new Information();
    }

    /**
     * Overloaded constructor.
     * Builds up the needed dependencies.
     *
     * @param recipientSalutation    a given salutation
     * @param recipient              a given recipient
     * @param recipientContactPerson a given contact person
     * @param recipientStreet        a given street
     * @param recipientBox           a given box
     * @param recipientPostalcode    a given postalcode
     * @param recipientCity          a given city
     * @param recipientCountry       a given country
     * @param recipientWeb           a given web site
     */
    private Recipient(String recipientSalutation, @NonNull String recipient, String recipientContactPerson,
                      @NonNull String recipientStreet, String recipientBox, @NonNull String recipientPostalcode,
                      @NonNull String recipientCity, @NonNull String recipientCountry, String recipientWeb,
                      Information information) {
        this();
        this.recipientSalutation = recipientSalutation;
        this.recipient = recipient;
        this.recipientContactPerson = recipientContactPerson;
        this.recipientStreet = recipientStreet;
        this.recipientBox = recipientBox;
        this.recipientPostalcode = recipientPostalcode;
        this.recipientCity = recipientCity;
        this.recipientCountry = recipientCountry;
        this.recipientWeb = recipientWeb;
        this.information.setCreatedBy(information.getCreatedBy());
        this.information.setModifiedBy(information.getModifiedBy());
    }

    /**
     * Copy constructor.
     * Copies the given instance.
     *
     * @param recipient a given recipient instance
     * @return a copy of the given instance
     */
    static Recipient copy(@NonNull Recipient recipient) {
        return new Recipient(recipient.recipientSalutation, recipient.getRecipient(), recipient.getRecipientContactPerson(),
                recipient.getRecipientStreet(), recipient.getRecipientBox(), recipient.recipientPostalcode, recipient.getRecipientCity(),
                recipient.getRecipientCountry(), recipient.getRecipientWeb(), recipient.getInformation());
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
        Recipient recipient = (Recipient) o;
        return recipientId == recipient.recipientId;
    }

    /**
     * Computes a hash code for this instance.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(recipientId);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (recipientSalutation != null && !recipientSalutation.isEmpty()) {
            sb.append(recipientSalutation);
            sb.append(System.lineSeparator());
        }
        if (recipient != null && !recipient.isEmpty()) {
            sb.append(recipient);
            sb.append(System.lineSeparator());
        }
        if (recipientContactPerson != null && !recipientContactPerson.isEmpty()) {
            sb.append(recipientContactPerson);
            sb.append(System.lineSeparator());
        }
        if (recipientBox != null && !recipientBox.isEmpty()) {
            sb.append(recipientBox);
            sb.append(System.lineSeparator());
        }
        if (recipientBox != null && !recipientBox.isEmpty()) {
            sb.append(recipientBox);
            sb.append(System.lineSeparator());
        }
        if (recipientStreet != null && !recipientStreet.isEmpty()) {
            sb.append(recipientStreet);
            sb.append(System.lineSeparator());
        }
        if (recipientPostalcode != null && !recipientPostalcode.isEmpty()) {
            sb.append(recipientPostalcode);
            sb.append(" ");
        }
        if (recipientCity != null && !recipientCity.isEmpty()) {
            sb.append(recipientCity);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
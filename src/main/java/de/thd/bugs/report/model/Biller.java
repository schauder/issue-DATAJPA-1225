package de.thd.bugs.report.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo entity for biller information.
 * Stores information concerning the invoice´s biller.
 *
 * @author tlang
 */
@Entity
@Table(name = "biller", schema = "public",
        indexes = {
                @Index(name = "index_biller", columnList = "biller"),
                @Index(name = "index_billerCampus", columnList = "billerCampus"),
                @Index(name = "index_billerContactPerson", columnList = "billerContactPerson"),
                @Index(name = "index_billerPostalcode", columnList = "billerPostalcode"),
                @Index(name = "index_billerCity", columnList = "billerCity")
        })
@Getter
@Setter
public class Biller implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @NotNull
    private long billerId;

    @Column(nullable = false)
    @Enumerated
    @NotNull(message = "{biller.billerCampus.notnull.message}")
    private Campus billerCampus;

    @Column(length = 50)
    @Size(max = 50, message = "{biller.billerSalutation.max.message}")
    private String billerSalutation;

    @Column(nullable = false, length = 150)
    @NotNull(message = "{biller.biller.notnull.message}")
    @Size(min = 2, max = 150, message = "{biller.biller.between.message}")
    private String biller;

    @Column(length = 50)
    @NotNull(message = "{biller.billerContactPerson.notnull.message}")
    @Size(min = 2, max = 50, message = "{biller.billerContactPerson.between.message}")
    private String billerContactPerson;

    @Column(length = 50)
    @NotNull(message = "{biller.billerContactDetails.notnull.message}")
    @Size(min = 2, max = 50, message = "{biller.billerContactDetails.between.message}")
    private String billerContactDetails;

    @Column(nullable = false, length = 100)
    @NotNull(message = "{biller.billerStreet.notnull.message}")
    @Size(min = 2, max = 100, message = "{biller.billerStreet.between.message}")
    private String billerStreet;

    @Column(length = 50)
    @Size(max = 50, message = "{biller.billerBox.max.message}")
    private String billerBox;

    @Column(nullable = false, length = 10)
    @NotNull(message = "{biller.billerPostalcode.notnull.message}")
    @Size(min = 2, max = 10, message = "{biller.billerPostalcode.between.message}")
    private String billerPostalcode;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{biller.billerCity.notnull.message}")
    @Size(min = 2, max = 50, message = "{biller.billerCity.between.message}")
    private String billerCity;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{biller.billerCountry.notnull.message}")
    @Size(min = 2, max = 50, message = "{biller.billerCountry.between.message}")
    private String billerCountry;

    @Column(nullable = false, length = 50)
    @Size(min = 7, max = 50, message = "{biller.billerWeb.between.message}")
    private String billerWeb;

    @Column(nullable = false)
    @Embedded
    @NotNull(message = "{information.notnull.message}")
    private Information information;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "biller", fetch = FetchType.LAZY, orphanRemoval = true)
    @NotNull
    private Set<Invoice> invoiceSet;

    /**
     * Standard Constructor.
     */
    public Biller() {
        this.billerCampus = Campus.THD;
        this.invoiceSet = new HashSet<>();
        this.information = new Information();
    }

    private Biller(Campus billerCampus, String billerSalutation, String biller, String billerContactPerson,
                   String billerContactDetails, String billerStreet, String billerBox, String billerPostalcode,
                   String billerCity, String billerCountry, String billerWeb) {
        this();
        this.billerCampus = billerCampus;
        this.billerSalutation = billerSalutation;
        this.biller = biller;
        this.billerContactPerson = billerContactPerson;
        this.billerContactDetails = billerContactDetails;
        this.billerStreet = billerStreet;
        this.billerBox = billerBox;
        this.billerPostalcode = billerPostalcode;
        this.billerCity = billerCity;
        this.billerCountry = billerCountry;
        this.billerWeb = billerWeb;
    }

    /**
     * Make a test biller.
     *
     * @return Biller
     */
    public static Biller makeTestBiller() {
        Biller biller = new Biller(Campus.THD, "Fa", "Test", "Herr Thomas Test",
                "test@test.com", "Testgasse 22", "", "64567", "Test",
                "Test", "www.test.com");
        biller.getInformation().setCreatedBy("tlang");
        biller.getInformation().setModifiedBy("tlang");
        return biller;
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
        Biller biller = (Biller) o;
        return billerId == biller.billerId;
    }

    /**
     * Computes a hash code for this instance.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(billerId);
    }

    /**
     * Readable version of the current instance.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s %s, %s, %s", biller, billerStreet, billerPostalcode, billerCity);
    }

    /**
     * Formats the biller contact details accordingly.
     *
     * @return String
     */
    public String formatBillerContactDetails() {
        final String[] splitBillerContactDetails = this.billerContactDetails.split(", ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String splitBillerContactDetail : splitBillerContactDetails) {
            boolean t = splitBillerContactDetail == null || splitBillerContactDetail.equals("null");
            if (t) continue;
            stringBuilder.append(splitBillerContactDetail);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /**
     * Formats the biller contact person and details accordingly.
     *
     * @return String
     */
    public String formatBillerContactPersonAndDetails() {
        return billerContactPerson +
                System.lineSeparator() +
                this.formatBillerContactDetails();
    }
}
package de.thd.bugs.report.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Modelclass for Entity Information.
 * Stores some general entity information.
 *
 * @author tlang
 */
@Embeddable
@Getter
@Setter
@ToString
public class Information implements Serializable {

    @Column(nullable = false, length = 50)
    @NotNull(message = "{information.createdBy.notnull.message}")
    private String createdBy;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{information.modifiedBy.notnull.message}")
    private String modifiedBy;

    @Column(nullable = false)
    @NotNull(message = "{information.creationDate.notnull.message}")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @NotNull(message = "{information.modificationDate.notnull.message}")
    @LastModifiedDate
    private LocalDateTime modificationDate;

    /**
     * Constructs a new information instance.
     */
    public Information() {
        creationDate = LocalDateTime.now();
        modificationDate = LocalDateTime.now();
    }

    /**
     * Overloaded constructor.
     *
     * @param createdBy the creator
     */
    public Information(String createdBy) {
        this();
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    /**
     * Constructs a new information instance with the given parameters.
     *
     * @param createdBy  created by
     * @param modifiedBy modified by
     */
    public Information(String createdBy, String modifiedBy) {
        this();
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    /**
     * Overloaded constructur.
     * Builds up the needed references
     *
     * @param markedNewBy          marked new by
     * @param furtherUsersOrGroups marked new by further users or groups
     */
    public Information(String markedNewBy, String[] furtherUsersOrGroups) {
        this(markedNewBy);
        String groups = Arrays.stream(furtherUsersOrGroups).collect(Collectors.joining(", "));
        this.createdBy += String.format(", %s", groups);
        this.modifiedBy += String.format(", %s", groups);
    }

    /**
     * Prints a nice creation date time string.
     * By the given locale.
     *
     * @return String
     */
    public String printLocalizedCreationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.getDefault());
        return this.creationDate.format(formatter);
    }

    /**
     * Prints a nice modification date time string.
     * By the given locale.
     *
     * @return String
     */
    public String printLocalizedModificationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.getDefault());
        return this.modificationDate.format(formatter);
    }
}

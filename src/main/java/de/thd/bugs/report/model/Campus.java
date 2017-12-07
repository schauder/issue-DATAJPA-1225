package de.thd.bugs.report.model;

import lombok.Getter;

/**
 * Enum holding the current Campus structure.
 *
 * @author tlang
 */
@Getter
public enum Campus {
    THD("Technische Hochschule Deggendorf"),
    GCBK("Gesundheitscampus Bad Kötzting"),
    TSZWBG("Technologie- und Studienzentrum Weißenburg"),
    TCF("Technologie Campus Freyung"),
    TCT("Technologie Campus Teisnach"),
    TCG("Technologie Campus Grafenau"),
    TCC("Technologie Campus Cham"),
    TAZS("Technologie Anwender Zentrum Spiegelau"),
    CSM("Campus Schloss Mariakirchen");


    private final String label;
    private final String value;

    /**
     * package-private standard constructor.
     * building up the dto
     *
     * @param label a given label
     */
    Campus(String label) {
        this.label = label;
        this.value = this.name();
    }

    /**
     * to string representation of the current instance.
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getLabel();
    }
}

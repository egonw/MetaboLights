
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xAxisUnitFIDType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="xAxisUnitFIDType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="milliseconds"/>
 *     &lt;enumeration value="microseconds"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "xAxisUnitFIDType")
@XmlEnum
public enum XAxisUnitFIDType {

    @XmlEnumValue("seconds")
    SECONDS("seconds"),
    @XmlEnumValue("milliseconds")
    MILLISECONDS("milliseconds"),
    @XmlEnumValue("microseconds")
    MICROSECONDS("microseconds");
    private final String value;

    XAxisUnitFIDType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XAxisUnitFIDType fromValue(String v) {
        for (XAxisUnitFIDType c: XAxisUnitFIDType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

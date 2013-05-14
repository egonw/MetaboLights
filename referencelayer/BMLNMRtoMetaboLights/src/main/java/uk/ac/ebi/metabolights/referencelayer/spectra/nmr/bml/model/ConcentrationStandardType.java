
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for concentrationStandardType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="concentrationStandardType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="internal"/>
 *     &lt;enumeration value="external"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "concentrationStandardType")
@XmlEnum
public enum ConcentrationStandardType {

    @XmlEnumValue("internal")
    INTERNAL("internal"),
    @XmlEnumValue("external")
    EXTERNAL("external");
    private final String value;

    ConcentrationStandardType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConcentrationStandardType fromValue(String v) {
        for (ConcentrationStandardType c: ConcentrationStandardType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

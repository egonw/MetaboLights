package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a MetaboLights reference layer compound.
 * 
 *
 * @author Pablo Conesa
 */
public class MetaboLightsCompound {

    // The internal identifier (for persistance)
    private long id;
    // The public accession number of this compound.
    private String accession;
    // The name of this compound
    private String name;
    // The description of this compound
    private String description;
    // Standard inchi of the compound
    private String inchi;
    // ChEBI id
    private String chebiId;
    // Formula
    private String formula;
    // Iupac Names (separated by |)
    private String iupacNames;

    private Boolean hasLiterature;

    private Boolean hasReactions;

    private Boolean hasSpecies;

    private Boolean hasPathways;

    private Boolean hasNMR;

    private Boolean hasMS;

    public Boolean getHasSpecies() {
        return hasSpecies;
    }

    public void setHasSpecies(Boolean hasSpecies) {
        this.hasSpecies = hasSpecies;
    }

    public Boolean getHasPathways() {
        return hasPathways;
    }

    public void setHasPathways(Boolean hasPathways) {
        this.hasPathways = hasPathways;
    }

    public Boolean getHasNMR() {
        return hasNMR;
    }

    public void setHasNMR(Boolean hasNMR) {
        this.hasNMR = hasNMR;
    }

    public Boolean getHasMS() {
        return hasMS;
    }

    public void setHasMS(Boolean hasMS) {
        this.hasMS = hasMS;
    }

    public Boolean getHasReactions() {
        return hasReactions;
    }

    public void setHasReaction(Boolean hasReactions) {
        this.hasReactions = hasReactions;
    }

    public Boolean getHasLiterature() {
        return hasLiterature;
    }

    public void setHasLiterature(Boolean hasLiterature) {
        this.hasLiterature = hasLiterature;
    }

    // Species associated with the metabolite
    private Collection<MetSpecies> metSpecies = new ArrayList<MetSpecies>();

    // Spectrums associated with the metabolite
    private Collection<Spectra> metSpectras = new ArrayList<Spectra>();


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the accession
     */
    public String getAccession() {
        return accession;
    }

    /**
     * @param accession the accession to set
     */
    public void setAccession(String accession) {
        this.accession = accession;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the inchi
     */
    public String getInchi() {
        return inchi;
    }

    /**
     * @param inchi the inchi to set
     */
    public void setInchi(String inchi) {
        this.inchi = inchi;
    }
    /**
     * @return the chebiId
     */
    public String getChebiId() {
        return chebiId;
    }

    public Collection<MetSpecies> getMetSpecies() {

        return metSpecies;
    }

    public Collection<Spectra> getMetSpectras() {
        return metSpectras;
    }

    /**
     * @param chebiId the chebiId to set
     */
    public void setChebiId(String chebiId) {
        this.chebiId = chebiId;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getIupacNames() {
        return iupacNames;
    }

    public void setIupacNames(String iupacNames) {
        this.iupacNames = iupacNames;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof MetaboLightsCompound))
            return false;

        MetaboLightsCompound comp1 = (MetaboLightsCompound)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(accession, comp1.accession).
                isEquals();

    }

}

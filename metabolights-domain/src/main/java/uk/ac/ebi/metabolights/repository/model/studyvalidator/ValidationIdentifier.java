package uk.ac.ebi.metabolights.repository.model.studyvalidator;

/**
 * Created by kalai on 18/09/15.
 */
public enum ValidationIdentifier {
    STUDY_TITLE(1),
    STUDY_DESCRIPTION(2),
    STUDY_TEXT(3),
    STUDY_CONTACT_EMAIL(4),
    FACTOR_NAME(11),
    FACTOR_IN_SAMPLES_ASSAYS(12),
    FACTOR_COLUMNS_SAMPLES_ASSAYS(13),
    ORGANISM_NAME(21),
    ORGANISM_PART(22),
    PUBLICATIONS(31),
    PUBLICATION_TITLE(32),
    PUBLICATION_AUTHORS(33),
    PUBLICATION_IDS(34),
    PUBLICATION_DOI(35),
    PUBLICATION_IDS_DOI(36),
    PROTOCOLS_ALL(41),
    PROTOCOLS_MINIMUM(42),
    PROTOCOLS_SAMPLE_COLLECTION(43),
    PROTOCOLS_TEXT(44),
    ASSAYS(61),
    ASSAY_PLATFORM(62),
    ASSAY_FILES(63),
    ASSAY_FILES_IN_FILESYSTEM(64),
    ASSAY_ALL_MAF_REFERENCE(65),
    ASSAY_ATLEAST_SOME_MAF_REFERENCE(66),
    ASSAYS_EMPTY_COLUMNS(67),
    ASSAY_MAF_FILE(81),
    ASSAY_CORRECT_MAF_FILE(82),
    SAMPLES(101),
    SAMPLES_EMPTY_COLUMNS(102),
    ASSAY_SAMPLE_NAMEMATCH(103),
    ISATAB_INVESTIGATION(121);

    private final int id;

    ValidationIdentifier(final int validationID) {
        id = validationID;
    }

    public int getID() {
        return id;
    }

}
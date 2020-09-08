package org.fao.geonet.repository;

public interface MetadataVoteBySessionRepositoryCustom {
    /**
     * Calculate the average of all the votes for the given metadata. <p> The method will take the
     * sum of all votes for the metadata and divide by the number of records (the average value)
     * </p>
     *
     * @param metadataId the metadata id.
     * @return the sum of all the votes.
     */
    int averageVote(int metadataId);

    /**
     * Calculate the sum of all the votes for the given metadata.
     * </p>
     *
     * @param metadataId the metadata id.
     * @return the sum of all the votes.
     */
    int sumVote(int metadataId);

    /**
     * Delete all the entities that are related to the indicated metadata.
     *
     * @param metadataId the id of the metadata.
     * @return the number of rows deleted
     */
    int deleteAllById_MetadataId(int metadataId);
}

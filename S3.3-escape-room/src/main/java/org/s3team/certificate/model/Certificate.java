package org.s3team.certificate.model;

import org.s3team.common.valueobject.Id;

public class Certificate {

    private final Id<Certificate> id;
    private final CertificateType certificateType;
    private final Reward reward;

    private Certificate(Id<Certificate> id, CertificateType certificateType, Reward reward){
        this.id = id;
        this.certificateType = certificateType;
        this.reward = reward;
    }

    public static Certificate createNew(CertificateType type, Reward reward) {
        return new Certificate(null,type,reward);
    }

    public static Certificate rehydrate(Id<Certificate> id, CertificateType type, Reward reward){
        return new Certificate(id,type,reward);
    }

    public Id<Certificate> getId() {
        return id;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public Reward getReward() {
        return reward;
    }

    @Override
    public String toString() {
        String hasRewardPart = reward.hasReward() ? ", reward = " + reward : "";
        return "Certificate = " +
                "id = " + id +
                ", certificateType = " + certificateType +
                hasRewardPart;
    }
}

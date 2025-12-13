package org.s3team.Exceptions;

import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;

public class CertificateNotFoundException extends RuntimeException {
    private final Id<Certificate> id;

    public CertificateNotFoundException(Id<Certificate> id) {
        super("Certificate not found: " + id);
        this.id = id;
    }

    public Id<Certificate> getId() {
        return id;
    }
}

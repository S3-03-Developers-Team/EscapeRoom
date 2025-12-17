package org.s3team.certificate.service;

import org.s3team.Exceptions.CertificateNotFoundException;
import org.s3team.certificate.dao.CertificateDao;
import org.s3team.certificate.model.Certificate;
import org.s3team.certificate.model.CertificateType;
import org.s3team.certificate.model.Reward;
import org.s3team.common.valueobject.Id;
import org.s3team.notification.NotificableEvent;
import org.s3team.notification.SendNotificationService;


import java.util.List;

public class CertificateService implements NotificableEvent {

    private final CertificateDao certificateDao;

    public CertificateService(CertificateDao certificateDao){
        this.certificateDao = certificateDao;
    }

    public Certificate createCertificate(CertificateType certificateType, Reward reward){
        Certificate certificate = Certificate.createNew(certificateType, reward);
        generateNotification("A new certificate has been created: "+certificate.toString());
        return certificateDao.save(certificate);
    }

    public Certificate getCertificateById(Id<Certificate> id){
        return certificateDao.findById(id).orElseThrow(() -> new CertificateNotFoundException(id));
    }

    public List<Certificate> getAllCertificates() {
        return certificateDao.findAll();
    }

    public Certificate updateCertificate(Certificate certificate) {
        certificateDao.findById(certificate.getId()).orElseThrow(()-> new CertificateNotFoundException(certificate.getId()));

        certificateDao.update(certificate);
        return certificate;
    }

    public void deleteCertificate(Id<Certificate> id) {
        boolean deleted = certificateDao.delete(id);
        if (!deleted) {
            throw new CertificateNotFoundException(id);
        }
    }

    @Override
    public void generateNotification(String message) {
        SendNotificationService newNotification = new SendNotificationService();
        newNotification.sendNotificationToSubscribers(message);
    }
}

package it.windtre.tremobilitycms.ui.views.admin.serviceitem;

import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;

public class ServiceitemWithService extends Serviceitem {

    private Service linkedService = null;
    public Service getLinkedService() {
        return this.getLinkedService();
    }
    public void setLinkedService(Service service) {
        this.linkedService = service;
    }

    public ServiceitemWithService(Serviceitem item, Service serv) {
        super.setCurrency(item.getCurrency());
        super.setDescription(item.getDescription());
        super.setDurationDescription(item.getDurationDescription());
        super.setDurationInterval(item.getDurationInterval());
        super.setDurationName(item.getDurationName());
        super.setDurationType(item.getDurationType());
        super.setId(item.getId());
        super.setInfozoneName(item.getInfozoneName());
        super.setInfozoneTypez(item.getInfozoneTypez());
        super.setName(item.getName());
        super.setService(item.getService());
        this.setLinkedService(serv);
    }

    public String getServiceDescription() {
        String s = "";
        if (linkedService != null) {
            s = s + String.valueOf(linkedService.getId()) + " - ";
            s = s + linkedService.getCity() + " - ";
            s = s + linkedService.getName();
        }
        return s;
    }

}

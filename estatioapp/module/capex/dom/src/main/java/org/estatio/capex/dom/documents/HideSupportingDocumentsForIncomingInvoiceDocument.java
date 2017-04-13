package org.estatio.capex.dom.documents;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.communications.dom.mixins.Document_communicationAttachments;
import org.incode.module.communications.dom.mixins.Document_communications;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.Document_backgroundCommands;
import org.incode.module.document.dom.mixins.T_documents;

import org.estatio.dom.invoice.DocumentTypeData;

@DomainService(nature = NatureOfService.DOMAIN)
public class HideSupportingDocumentsForIncomingInvoiceDocument extends AbstractSubscriber {

    @EventHandler
    @Subscribe
    public void on(T_documents.ActionDomainEvent ev) {
        hideIfIncomingInvoiceDocument(ev);
    }

    @EventHandler
    @Subscribe
    public void on(Document_backgroundCommands.ActionDomainEvent ev) {
        hideIfIncomingInvoiceDocument(ev);
    }

    @EventHandler
    @Subscribe
    public void on(Document_communicationAttachments.ActionDomainEvent ev) {
        hideIfIncomingInvoiceDocument(ev);
    }

    @EventHandler
    @Subscribe
    public void on(Document_communications.ActionDomainEvent ev) {
        hideIfIncomingInvoiceDocument(ev);
    }

    private void hideIfIncomingInvoiceDocument(final org.apache.isis.applib.services.eventbus.ActionDomainEvent ev) {
        switch (ev.getEventPhase()) {
        case HIDE:
            Object mixedIn = ev.getMixedIn();
            if (mixedIn instanceof Document) {
                Document document = (Document) mixedIn;
                if (DocumentTypeData.INCOMING_INVOICE.isDocTypeFor(document)) {
                    ev.hide();
                }
            }
        }
    }


}

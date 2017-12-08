package de.thd.bugs.report.service;

import de.thd.bugs.report.model.Invoice;
import de.thd.bugs.report.model.InvoiceProjection;
import de.thd.bugs.report.model.InvoiceState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Invoice service layer.
 * Adds some additional business logic on top of the repository
 * layer.
 *
 * @author tlang
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvoiceService implements IInvoiceService {


    private final
    @NonNull
    IInvoiceRepository invoiceRepository;

    /**
     * Creates a new invoice.
     *
     * @param invoice a given new invoice.
     * @throws Exception a given exception
     */
    @Override
    @Async
    public Future<String> invoiceInvoice(Invoice invoice) {
        return new AsyncResult(invoiceRepository.save(Invoice.makeTestInvoice()));
    }

    /**
     * Finds all invoices projected by the needed values.
     *
     * @param createdBy    a given user login
     * @param apiGroups    given api groups
     * @param invoiceState a given invoice state
     * @return Future of Iterable of InvoiceProjection
     */
    @Override
    @Async
    public Future<Iterable<InvoiceProjection>> findAllProjectedByUserAndApiGroupsAndInvoiceState(@NonNull String createdBy,
                                                                                                 @NonNull String apiGroups,
                                                                                                 @NonNull InvoiceState invoiceState) {
        if (createdBy.isEmpty()) return new AsyncResult<>(null);

        String[] stringArray = apiGroups.split(", ");
        String[] newStringArray = Arrays.copyOf(stringArray, stringArray.length + 1);
        newStringArray[newStringArray.length - 1] = apiGroups;

        final Iterable<InvoiceProjection> temp = invoiceRepository.findAllProjectedByInvoiceStateAndUser(createdBy, invoiceState);
        final Iterable<InvoiceProjection> userInvoices = invoiceRepository.findAllProjectedByInvoiceStateAndSimilarUserOrApiGroup("%tlang%|%Seminare%", invoiceState.ordinal());

        //allProjectedByInvoiceStateAndSimilarUserOrApiGroup.forEach(b -> System.out.println(String.valueOf(b.getInvoiceId())));
        //allProjectedByInvoiceStateAndLikeUserOrApiGroup.forEach(b -> System.out.println(String.valueOf(b.getInvoiceId())));
//        userInvoices.forEach(relevantInvoices::add);
//        for (int i = 1; i < stringArray.length; i++) {
//            String group = newStringArray[i];
//            final Iterable<InvoiceProjection> groupInvoices = invoiceRepository.findAllProjectedByInvoiceStateAndUserOrApiGroup(invoiceState, group, createdBy);
//            groupInvoices.forEach(relevantInvoices::add);
//        }
//
//        final Stream<InvoiceProjection> sortedInvoices = relevantInvoices.stream().sorted(Comparator.comparing(InvoiceProjection::getInvoiceDate).reversed());
//        final List<InvoiceProjection> collect = sortedInvoices.collect(Collectors.toList());
//        return new AsyncResult<>(collect);

        final List<InvoiceProjection> collect = StreamSupport.stream(userInvoices.spliterator(), false).collect(Collectors.toList());
        return new AsyncResult<>(collect);
    }
}

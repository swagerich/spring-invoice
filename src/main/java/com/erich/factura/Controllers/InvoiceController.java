package com.erich.factura.Controllers;

import com.erich.factura.Entity.Client;
import com.erich.factura.Entity.Invoice;
import com.erich.factura.Entity.InvoiceDetail;
import com.erich.factura.Entity.Product;
import com.erich.factura.Services.Impl.ClientServiceImpl;
import com.erich.factura.Services.Impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("invoice")
@RequestMapping("/invoice")
public class InvoiceController {

    private final ClientServiceImpl clientService;

    private final ProductServiceImpl productService;

    @GetMapping("/form/{id}")
    public String create(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Client client = clientService.update(id);
        if (client == null) {
            flash.addAttribute("error", "El cliente no existe en la bd");
            return "redirect:/listar";
        }
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        model.addAttribute("invoice", invoice);
        model.addAttribute("titulo", "Crear Invoice");
        return "invoice/form";
    }

    @GetMapping("/ver/{id}")
    public String verId(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Invoice invoice = clientService.fetchInvoceById(id); //Aplique optimizacion de JPQ (sino usaria findInvoiceById)

        if (invoice != null) {
            model.addAttribute("invoice", invoice);
            model.addAttribute("titulo", "Invoice ".concat(invoice.getDescription()));
            return "invoice/ver";
        }

        return flash.addFlashAttribute("error", "Invoice not exist in bd").toString();
    }


    @Operation(summary = "We load and search for the product by name ")
    @ResponseBody
    @GetMapping(value = "/load-products/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> loadProduct(@Parameter(name = "Primary key of type string") @PathVariable String name) {
        return productService.findByName(name);
    }

    @PostMapping("/form")
    public String save(@Valid Invoice invoice, BindingResult result, Model model,
                       @RequestParam(name = "detail_id[]", required = false) Long[] detailId,
                       @RequestParam(name = "quantity[]", required = false) Integer[] quantityId,
                       RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Create Invoce");
            return "invoice/form";
        }
        if (detailId == null || detailId.length == 0) {
            model.addAttribute("titulo", "Create Invoce");
            model.addAttribute("error", "Error : The invoice not line");
            return "invoice/form";
        }
        for (int i = 0; i < quantityId.length; i++) {
            Product p = productService.findProductById(detailId[i]);
            InvoiceDetail detail = new InvoiceDetail();
            detail.setQuantity(quantityId[i]);
            detail.setProduct(p);
            invoice.addDetailInvoce(detail);
        }

        clientService.saveInvoice(invoice);
        status.setComplete();
        //flash.addFlashAttribute("success","Invoce Create exit");
        return "redirect:/ver/" + invoice.getClient().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoce(@PathVariable Long id, RedirectAttributes flash) {
        Invoice invoice = clientService.findInvoiceById(id);
        if (invoice != null) {
            clientService.deleteInvoice(id);
            //flash.addAttribute("success", "invoice deleted successfully");
            return "redirect:/ver/" + invoice.getClient().getId();
        }
        flash.addFlashAttribute("error", "invoice does not exist in bd");
        return "redirect:/listar";
    }
}

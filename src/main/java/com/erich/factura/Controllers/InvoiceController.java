package com.erich.factura.Controllers;

import com.erich.factura.Entity.Client;
import com.erich.factura.Entity.Invoice;
import com.erich.factura.Services.Impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes("invoice")
@RequestMapping("/invoice")
public class InvoiceController {

    private final ClientServiceImpl clientService;

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
}

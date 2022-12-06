package com.erich.factura.Controllers;

import com.erich.factura.Entity.Client;
import com.erich.factura.Entity.Invoice;
import com.erich.factura.Entity.Product;
import com.erich.factura.Services.Impl.ClientServiceImpl;
import com.erich.factura.Services.Impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "We load and search for the product by name ")
    @ResponseBody
    @GetMapping(value = "/load-products/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> loadProduct(@Parameter(name = "Primary key of type string") @PathVariable String name) {
        return productService.findByName(name);
    }
}

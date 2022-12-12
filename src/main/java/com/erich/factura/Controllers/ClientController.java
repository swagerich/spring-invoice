package com.erich.factura.Controllers;


import com.erich.factura.Entity.Client;
import com.erich.factura.Services.Impl.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@SessionAttributes("client")
public class ClientController {

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/listar")
    public String listar(/*@RequestParam(name = "page", defaultValue = "0") int page,*/ Model model) {
        /*Pageable pageable =  PageRequest.of(page,4, Sort.Direction.DESC);
        Page<Client> clients = clientService.page(pageable);*/

        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("all", clientService.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Client client = new Client();
        model.put("titulo", "Formulario de Client");
        model.put("client", client);
        return "form";
    }

    @GetMapping("/ver/{id}")
    public String verPhoto(@PathVariable Long id, Model model) {
        Client client = clientService.fetchByIdWithInvoce(id);  //Aplique optimizacion de JPQ (sino usaria update)
        if (client == null) {
            return "redirect/listar";
        }
        model.addAttribute("client", client);
        model.addAttribute("titulo", "Detalle del Cliente" + client.getName());
        return "ver";
    }


    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, SessionStatus session) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Cliente");
            return "form";
        }
        clientService.save(client,foto);
        session.setComplete();
        return "redirect:listar";
    }

    @RequestMapping("/form/{id}")
    public String update(@PathVariable Long id, Map<String, Object> model) {
        Client client;
        if (id > 0) {
            client = clientService.update(id);
            if (client == null)
                return "redirect:/listar";
        } else {
            return "redirect:/listar";
        }
        model.put("client", client);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/listar";
    }
}

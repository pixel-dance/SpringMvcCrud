package org.example.controllers;

import org.example.dao.DatabaseService;
import org.example.dao.FakeDbService;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private DatabaseService personDAO;

    @Autowired
    public PeopleController(@Qualifier("pureJdbcService")DatabaseService personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "people/login";
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('people:read')")
    public String index(Model model){

        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('people:read')")
    public String show(@PathVariable("id") int id, Model model){

        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('people:write')")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('people:write')")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('people:write')")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('people:write')")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if (bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('people:write')")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}

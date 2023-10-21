package com.atabek.springcourse.controllers;

import com.atabek.springcourse.dao.PersonDAO;
import com.atabek.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("people")
public class PeopleController {
    private PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person){
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                         @RequestParam(value = "_method", required = false) String method){
        if("PATCH".equalsIgnoreCase(method)){
            personDAO.update(id, person);
            return "redirect:/people";
        }
        return null;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id,
                         @RequestParam(value = "_method", required = false) String method){
        if("DELETE".equalsIgnoreCase(method)){
            personDAO.delete(id);
            return "redirect:/people";
        }
        return null;
    }
}



















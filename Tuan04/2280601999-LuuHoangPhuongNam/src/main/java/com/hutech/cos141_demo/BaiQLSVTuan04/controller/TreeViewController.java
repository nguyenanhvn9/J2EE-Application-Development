package com.hutech.cos141_demo.BaiQLSVTuan04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hutech.cos141_demo.BaiQLSVTuan04.service.FacultyService;

@Controller
@RequestMapping("/BaiQLSVTuan04")
public class TreeViewController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/tree-view")
    public String treeView(Model model) {
        // Lấy danh sách các khoa, mỗi khoa có subjects, mỗi subject có studentClasses, mỗi class có enrollments
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "BaiQLSVTuan04/tree-view";
    }
} 
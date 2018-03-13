package com.jk.kotlinjpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/studnet")
class StudentController{
    @Autowired
    lateinit var studentRepository:StudentRepository
    @RequestMapping("findByName")
    fun finByName(name:String):List<Student>{
        return studentRepository.findByName(name);
    }
    @RequestMapping("add")
    fun add(name:String):String{
        val student=Student(id=null,name = name)
        studentRepository.save(student)
        return "success"
    }
}

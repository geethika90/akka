package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;

public class StudentFinder extends AbstractActor {
    public java.lang.String message;
    public ActorRef schoolActor;
    public java.lang.String matchedStudent;

    public StudentFinder(String message, ActorRef schoolActor) {
        this.message = message;
        this.schoolActor = schoolActor;
    }

    public static class Student {


        private String name;
        private String address;


        public Student() {
        }

        public Student(String name) {
            this.name = name;
        }

        public Student(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    static public Props props(String message, ActorRef schoolActor) {
        return Props.create(com.lightbend.akka.sample.StudentFinder.class, () -> new com.lightbend.akka.sample.StudentFinder(message, schoolActor));
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    static Map<String, Student> studentMap = new HashMap<>();

    static {
        studentMap.put("Geethika", new Student("Geethika", "7F, Infra Vantage, Kochi"));
        studentMap.put("Keerthana", new Student("Keerthana", "3D, Olive, Kochi"));
    }

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(Student.class, studentName -> {
                    log.info("Got message: " + studentName.name);
                    Student student = studentMap.get(studentName.name);
                    this.matchedStudent = "Name: " + student.getName() + "    Address: " + student.getAddress();
                    sender().tell(new School.Student(this.matchedStudent), ActorRef.noSender());
                    //Why was schoolActor.tell(new School.Student(this.matchedStudent), ActorRef.noSender()); not resolved
                }).matchAny(message -> log.info("In matchAny of StudentFinder"))
                .build();
    }
}

package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Student extends AbstractActor {


    private String message;

    static public Props props() {
        return Props.create(Student.class, () -> new Student());
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(School.Student.class, student -> {
                    log.info("Received student name who transitioned as school leader: " + student.message);
                })
                .build();
    }
}

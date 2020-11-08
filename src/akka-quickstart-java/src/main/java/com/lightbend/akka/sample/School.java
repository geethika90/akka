package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class School extends AbstractActor {
    public java.lang.String message;
    public ActorRef studentFinderActor;
    public java.lang.String studentToBeMatched;

    static public Props props(String message, ActorRef studentFinderActor) {
        return Props.create(School.class, () -> new School(message, studentFinderActor));
    }

    public static class MatchedStudent {

        private String message;

        public MatchedStudent() {
        }

        public MatchedStudent(String message) {
            this.message = message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static public class Student {
        public final String message;

        public Student(String message) {
            this.message = message;
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public School(String message, ActorRef studentFinderActor) {
        this.message = message;
        this.studentFinderActor = studentFinderActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MatchedStudent.class, studentName -> {
                    log.info("Name received: " + studentName.message);
                    studentToBeMatched = studentName.message;
                    log.info("Name to be processed: " + studentToBeMatched);
                    studentFinderActor.tell(new StudentFinder.Student(studentToBeMatched), getSelf());
                })
                .match(Student.class, student -> {
                            log.info("Found matched: " + student.message);
                            Thread.sleep(2000);
                            System.out.println("-----BECOME/UNBECOME----");
                            getContext().become(beASchoolLeader());
                        }
                )
                .build();
    }

    private Receive beASchoolLeader() {
        log.info("In become ");
        return receiveBuilder()
                .matchAny(student1 -> {
                    log.info("Am a leader");
                    getContext().unbecome();
                })
                .build();

    }
}

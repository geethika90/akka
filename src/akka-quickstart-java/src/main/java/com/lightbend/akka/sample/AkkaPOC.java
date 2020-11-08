package com.lightbend.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

import java.io.IOException;

public class AkkaPOC {
    public static void main(String[] args) throws InterruptedException {

        Science sc=new Science();
        sc.setMarks(99);
        Subject sub=sc;
        sub.

        final ActorSystem system = ActorSystem.create("SchoolSystem");
        try {
            //#create-actors
            final ActorRef studentActor =
                    system.actorOf(Student.props(), "studentActor");
            final ActorRef studentFinderActor =
                    system.actorOf(StudentFinder.props("Hello", studentActor), "studentFinderActor");
            final ActorRef schoolActor =
                    system.actorOf(School.props("FindAddressByStudent", studentFinderActor), "schoolActor");


            //#create-actors

            //#main-send-messages
            System.out.println("-----SEND/RECEIVE----");
            schoolActor.tell(new School.MatchedStudent("Geethika"), ActorRef.noSender());

            Thread.sleep(5000);
            ActorSelection actor = system.actorSelection("/user/schoolActor");
            actor.tell(new School.Student("Geethika"), ActorRef.noSender());

            Thread.sleep(2000);
            studentActor.tell(new School.Student("Keerthana"), ActorRef.noSender());

            Thread.sleep(4000);
            System.out.println("-----ACTOR SELECTION----");

            ActorSelection actor1 = system.actorSelection("/user/studentFinderActor");
            actor1.tell(new School.Student("Geethika"), ActorRef.noSender());

            //#main-send-messages

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ioe) {
        } finally {
            system.terminate();
        }
    }
}

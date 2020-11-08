package com.lightbend.akka.sample;

import lombok.Data;

/**
 * Created by a-7999 on 01/10/19.
 */

@Data
public class Science extends Subject{
int marks=0;

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Science() {
    }
}

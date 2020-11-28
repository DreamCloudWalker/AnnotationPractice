package com.dengjian.annotationpractice.intdef;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class WeekDayTest {
    private static final int SUNDAY = 0;
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    public static @WeekDay int mCurrentDay = SUNDAY;

    @IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    public @interface WeekDay{

    }

    public static void main(String... args) {
        setCurrentDay(MONDAY);
    }

    private static void setCurrentDay(@WeekDay int day) {
        mCurrentDay = day;
        System.out.println("mCurrentDay = " + mCurrentDay);
    }
}

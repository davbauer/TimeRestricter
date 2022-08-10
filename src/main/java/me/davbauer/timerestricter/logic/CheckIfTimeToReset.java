package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;

import java.time.LocalDateTime;
import java.util.List;

public class CheckIfTimeToReset {
    private final TimeRestricter main;
    private final LogicFunctions cf;

    private boolean alreadyChanged = false;

    public CheckIfTimeToReset(TimeRestricter main) {
        this.main = main;
        this.cf = new LogicFunctions(main);
    }

    public boolean checkRoutine() {
        int getHour = main.getConfig().getInt("fillUpTimeHour");
        List<Integer> getDays = main.getConfig().getIntegerList("fillUpTimeDays");
        int nowHour = LocalDateTime.now().getHour();

        LocalDateTime nowdatetime = LocalDateTime.now();

        int targetTime = (getHour * 60) + main.getConfig().getInt("fillUpTimeMinute");
        int nowTime = (nowHour * 60) +  nowdatetime.getMinute();
        int nowDay = nowdatetime.getDayOfWeek().getValue();

        if (getDays.contains(nowDay) && nowTime == targetTime && !this.alreadyChanged) {
            alreadyChanged = true;
            return true;
        } else if (nowTime > targetTime ) {
            alreadyChanged = false;
        }

        return false;
    }
}

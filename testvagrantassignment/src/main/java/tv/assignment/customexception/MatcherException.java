package tv.assignment.customexception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatcherException extends Exception {

    private String message;

    public MatcherException(boolean isConditionMatched, boolean isWindSpeedMatched, boolean isTempDegreeMatched,
                            boolean isTempFarhenheitMatched, boolean isHumidityMatched) {
        StringBuilder failureMessage = new StringBuilder();
        if (!isConditionMatched)
            failureMessage.append("Condition is not matched and ");
        failureMessage.append("One or more comparators(");
        if (!isTempDegreeMatched)
            failureMessage.append(" temperature in degrees,");
        if (!isTempFarhenheitMatched)
            failureMessage.append(" temperature in farheneit,");
        if (!isWindSpeedMatched)
            failureMessage.append(" wind speed,");
        if (!isHumidityMatched)
            failureMessage.append(" humidity");
        failureMessage.append("), returned a value outside the acceptable range(s)");
        message = String.valueOf(failureMessage);
    }
}

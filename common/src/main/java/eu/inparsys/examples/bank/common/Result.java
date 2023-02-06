package eu.inparsys.examples.bank.common;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import io.vavr.control.Either;

import java.util.List;
import java.util.Objects;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Collections.emptyList;

public class Result {

    public static Result success() {
        return success(emptyList());
    }

    public static Result success(DomainOutgoingEvent event) {
        return new Result(right(new Success(List.of(event))));
    }

    public static Result success(DomainOutgoingEvent... events) {
        return new Result(right(new Success(List.of(events))));
    }

    public static Result success(List<DomainOutgoingEvent> events) {
        return new Result(right(new Success(events)));
    }

    public static Result failure(String reason) {
        return new Result(left(new Failure(reason)));
    }

    final Either<Failure, Success> result;

    private Result(Either<Failure, Success> result) {
        this.result = result;
    }

    public boolean isFailure() {
        return result.isLeft();
    }

    public boolean isSuccessful() {
        return result.isRight();
    }

    public String reason() {
        if (result.isLeft()) {
            return result.getLeft().reason;
        }
        return "OK";
    }


    public List<DomainOutgoingEvent> events() {
        return result
                .map(success -> success.events)
                .getOrElse(emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return Objects.equals(result, result1.result);
    }

}

class Success {

    final List<DomainOutgoingEvent> events;

    Success(List<DomainOutgoingEvent> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Success success = (Success) o;
        return Objects.equals(events, success.events);
    }

}

class Failure {

    //probably errorCode too... - see ADR-10
    final String reason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Failure failure = (Failure) o;
        return Objects.equals(reason, failure.reason);
    }


    Failure(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Failure{" +
                "reason='" + reason + '\'' +
                '}';
    }
}

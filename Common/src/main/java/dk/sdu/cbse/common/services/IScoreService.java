package dk.sdu.cbse.common.services;

public interface IScoreService {

    /** Adds 1 to score, returns new total. Null if service is down */
    Integer addPoint();
}

import dk.sdu.cbse.common.services.IScoreService;

module ScoreClient {
    requires Common;
    requires spring.web;

    provides IScoreService with dk.sdu.cbse.scoreclient.ScoreClient;
}

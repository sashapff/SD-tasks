package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import utils.SearcherResult;
import utils.Timeout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MasterActor extends AbstractActor {
    private static Duration MASTER_TIMEOUT;
    private final List<SearcherResult> resultList = new ArrayList<>();
    private ActorRef sender;

    public MasterActor(Duration duration) {
        MASTER_TIMEOUT = duration;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, request -> {
            sender = getSender();

            context().actorOf(
                    Props.create(ChildActor.class, "google"),
                    "google"
            ).tell(request, self());
            context().actorOf(
                    Props.create(ChildActor.class, "yandex"),
                    "yandex"
            ).tell(request, self());
            context().actorOf(
                    Props.create(ChildActor.class, "bing"),
                    "bing"
            ).tell(request, self());
            context().system().scheduler().scheduleOnce(
                    MASTER_TIMEOUT,
                    () -> self().tell(new Timeout(), ActorRef.noSender()),
                    context().system().dispatcher());
        }).match(Timeout.class, stop -> {
            sender.tell(resultList, self());
            context().stop(self());
        }).match(SearcherResult.class, response -> {
            resultList.add(response);

            if (resultList.size() == 3) {
                sender.tell(resultList, self());
                context().stop(self());
            }
        }).build();
    }

}


package top.dteam.earth.clock.utils;

import io.reactiverse.pgclient.PgRowSet;
import io.reactiverse.pgclient.PgTransaction;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import org.slf4j.Logger;

public class SimpleCommiter implements Handler<AsyncResult<PgRowSet>> {

    private String errors;
    private Logger logger;
    private PgTransaction tx;

    public SimpleCommiter(String errors, Logger logger, PgTransaction tx) {
        this.errors = errors;
        this.logger = logger;
        this.tx = tx;
    }

    @Override
    public void handle(AsyncResult<PgRowSet> asyncResult) {
        tx.commit(ar -> {
            if (ar.succeeded()) {
                logger.info("Transaction succeeded");
            } else {
                logger.error(errors, ar.cause());
                tx.rollback();
            }
        });
    }
}

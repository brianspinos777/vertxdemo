package com.example;


//import com.model.Person;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class App extends AbstractVerticle {

    @Override
    public void start() {

        /*
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        }).listen(8080);
        */


        //Create the router
        Router router = Router.router(Vertx.vertx());

        //Allow to get the body of HTTP request
        router.route().handler(BodyHandler.create());

        //Create endpoints
        router.get("/foos").handler(this::all);
        router.get("/foos/:id").handler(this::find);
        router.post("/foos").handler(this::create);
        router.put("/foos/:id").handler(this::update);
        router.delete("/foos/:id").handler(this::delete);

        //Create the server
        Vertx.vertx().createHttpServer().requestHandler(router::accept).listen(8080);
    }

    //
    // Endpoints handlers
    //

    private void all(RoutingContext context){
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encode(new JsonObject().put("message", "all Foos")));
    }

    private void find(RoutingContext context){
        String id = context.request().getParam("id");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encode(new JsonObject().put("message", "Foo #" + id)));
    }

    private void create(RoutingContext context){
        String val = context.getBodyAsString();

        JsonObject obj = new JsonObject()
                .put("message", "Foo created!")
                .put("body", val);

        context.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encode(obj));
    }

    private void update(RoutingContext context){
        String id = context.request().getParam("id");
        String val = context.getBodyAsString();

        JsonObject obj = new JsonObject()
                .put("message", "Foo updated!")
                .put("id", id)
                .put("body", val);

        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encode(obj));
    }

    private void delete(RoutingContext context){
        String id = context.request().getParam("id");

        JsonObject obj = new JsonObject()
                .put("message", "Foo deleted!")
                .put("id", id);

        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encode(obj));
    }
}

// https://stackoverflow.com/questions/32205477/how-to-start-vert-x-server-from-intellij-idea
// on how to run vertx app


// code example:
// https://vertx.io/